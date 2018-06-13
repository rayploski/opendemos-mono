package com.redhat.opendemos.view;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.time.LocalTime;


@FacesConverter("ltconv")
public class LocalTimeConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value != null && !"".equals(value)){

            String[] output = value.split(":");

            if (output.length == 2)
            {
                int hour = Integer.parseInt(output[0]);
                int minute = Integer.parseInt(output[1]);
                return LocalTime.of(hour,minute);
            } else return null;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null){
            LocalTime localTime = (LocalTime)value;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(localTime.getHour());
            stringBuilder.append(":");
            stringBuilder.append(localTime.getMinute());
            return stringBuilder.toString();

        }

        return null;
    }
}
