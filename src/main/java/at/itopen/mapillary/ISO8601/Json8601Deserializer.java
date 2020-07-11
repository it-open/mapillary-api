/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.ISO8601;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roland
 */
public class Json8601Deserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
        try {
            return ISO8601.toDate(arg0.getText());
        } catch (ParseException ex) {
            Logger.getLogger(Json8601Deserializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
