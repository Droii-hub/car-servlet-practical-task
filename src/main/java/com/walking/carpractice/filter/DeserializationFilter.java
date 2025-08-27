package com.walking.carpractice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walking.carpractice.ApplicationException;
import com.walking.carpractice.ErrorCode;
import com.walking.carpractice.model.Car;
import com.walking.carpractice.model.CarIdentifier;
import com.walking.carpractice.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeserializationFilter extends HttpFilter {
    private ObjectMapper objectMapper;

    @Override
    public void init(){
        objectMapper =(ObjectMapper) getFilterConfig().getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        // Если в теле не JSON или тело пустое, то пропускаем данный фильтр
        if (!"application/json".equals(req.getContentType()) || req.getContentLength() == 0) {
            chain.doFilter(req, res);
            return;
        }

        // Получаем тело как массив байт. Jackson умеет работать и с InputStream,
        // но данный подход нагляднее для демонстарции
        byte[] jsonBody = req.getInputStream().readAllBytes();
        Object pojoBody;
        try {
            // Получаем целевой тип через отдельный метод. Как он работает на самом деле - не важно в рамках примера
            Class<?> targetType = getTargetType(req);

            // Десериализуем JSON. Допустим, что ObjectMapper представлен полем и инициализирован через init()
            pojoBody = objectMapper.readValue(jsonBody, targetType);
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.WRONG_REQUEST, e);
        }
        // Добавляем полученный Java-объект в атрибуты запроса для дальнейшего поулчения в сервлете
        req.setAttribute("pojoRequestBody", pojoBody);

        // Переходим далее по цепочке. Аналогичное поведение можно достигнуть, вызвав `super.doFilter(req, res, chain);`.
        // Строка ниже описывает поведение данного метода по умолчанию (в HttpFilter)
        chain.doFilter(req, res);
    }

    private Class<?> getTargetType(HttpServletRequest req) {
        String path=req.getServletPath();
        String method=req.getMethod();
        if (path.equals("/car")&method.equals("POST"))
            return Car.class;
        if (path.equals("/car")&method.equals("PUT"))
            return Car.class;
        if (path.equals("/car")&method.equals("GET"))
            return CarIdentifier.class;
        if (path.equals("/car")&method.equals("DELETE"))
            return CarIdentifier.class;
        if (path.equals("/registration")&method.equals("POST"))
            return User.class;
        if (path.equals("/login")&method.equals("POST"))
            return User.class;
        if (path.equals("/user")&method.equals("PATCH"))
            return User.class;

        throw new RuntimeException("Unsupported path or method");
    }
}
