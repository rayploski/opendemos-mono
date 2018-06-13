package com.redhat.opendemos.view;


import jdk.vm.ci.meta.Local;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@FacesConverter("ldconv")
public class LocalDateConverter implements Converter{

    private static final String DATE_FMT = "dd MMM yyyy";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FMT).withLocale( Locale.ENGLISH );

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !"".equals(value)){

            LocalDate localDate = LocalDate.parse(value, dtf);
            return localDate;
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value != null){

                LocalDate date = (LocalDate)value;
                return date.format(dtf);
        }

        return null;
    }
}
