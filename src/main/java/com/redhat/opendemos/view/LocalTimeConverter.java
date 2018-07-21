package com.redhat.opendemos.view;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


@FacesConverter("ltconv")
public class LocalTimeConverter implements Converter {
    private static final DateTimeFormatter timeDTF = DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.ENGLISH);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {


        if (value != null && !"".equals(value)){
            LocalTime localTime = LocalTime.parse(value, timeDTF);
            return localTime;
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null){
            LocalTime localTime = (LocalTime)value;
            return localTime.toString();
        }

        return null;
    }
}
