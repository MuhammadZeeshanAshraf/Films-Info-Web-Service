package com.ztech.enterpriseprogramming.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ztech.enterpriseprogramming.Model.BaseFilm;
import com.ztech.enterpriseprogramming.Model.Film;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.List;

public class UtilSerivces {

    public static String ListToJSONConverter(List list) throws JsonProcessingException {
        String arrayToJson = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            arrayToJson = objectMapper.writeValueAsString(list);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return arrayToJson;
    }

    public static String ListToXMLConverter(List<Film> list) throws IOException, JAXBException {
        String xml = "";
        try {
            BaseFilm films = new BaseFilm();
            films.setFilms(list);
            JAXBContext context = JAXBContext.newInstance(BaseFilm.class, Film.class);
            Marshaller jaxbMarshaller = context.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            OutputStream os = new FileOutputStream("Film.xml");
            jaxbMarshaller.marshal(films, os);
            File file = new File("Film.xml");
            XmlMapper xmlMapper = new XmlMapper();
            xml = inputStreamToString(new FileInputStream(file));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return xml;
    }

    public static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }
}




