package com.codeup.deramussweedenvillegascapstone.controllers;

import com.codeup.deramussweedenvillegascapstone.models.CurrentWeather;
import com.codeup.deramussweedenvillegascapstone.services.LiveWeatherService;
import jakarta.websocket.DeploymentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrentWeatherController {

    private final LiveWeatherService liveWeatherService;



    public CurrentWeatherController(LiveWeatherService liveWeatherService) {
        this.liveWeatherService = liveWeatherService;
    }
    @GetMapping("/current-weather")
    public String getCurrentWeather(Model model) throws DeploymentException {
//        CurrentWeather currentWeather = new CurrentWeather("Clear", BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN);
//        model.addAttribute("currentWeather", currentWeather);

        model.addAttribute("currentWeather", liveWeatherService.getCurrentWeather("Austin","us"));

        return "/current-weather";
    }
}