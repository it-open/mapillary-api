/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.ISO8601;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Date;

/**
 * Date to ISO8601 String converter for Jackson
 *
 * @author roland
 */
public class Json8601Serializer extends JsonSerializer<Date> {

    /**
     * Serializer for jackson
     *
     * @param arg0 The original Date
     * @param arg1 The JSon Gernerator
     * @param arg2 Serializer
     * @throws IOException
     */
    @Override
    public void serialize(Date arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
        arg1.writeString(ISO8601.fromDate(arg0));
    }

}
