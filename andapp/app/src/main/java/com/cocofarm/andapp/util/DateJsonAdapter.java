package com.cocofarm.andapp.util;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@JsonAdapter(DateJsonAdapter.class)
public class DateJsonAdapter extends TypeAdapter<Date> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy, h:mm:ss a", Locale.US);

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            String formattedDate = DATE_FORMAT.format(value);
            out.value(formattedDate);
        }
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        String dateString = in.nextString();
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            throw new JsonParseException("Error parsing date: " + dateString, e);
        }
    }
}
