package com.example.wandukong.controller.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

public class LocalDateFormatter implements Formatter<LocalDate> {

  @Override
  public LocalDate parse(String text, Locale locale) throws ParseException {
    // text를 어떻게 localdate로 바꿀 건지
    
    return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  @Override
  public String print(LocalDate object, Locale locale) {
    // localdate를 어떻게 문자열로 만들것인지

    return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
  }

}
