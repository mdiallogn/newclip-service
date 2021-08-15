package com.example.authenticationapp.proxyWS;

import com.example.authenticationapp.model.sylobe.Article;
import com.example.authenticationapp.model.sylobe.Kit;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class KitProxy extends DefaultHandler {

    private final String LINE_RES_WS = "ligneResultatWS";
    private final String VALUE = "valeur";

    private Kit kit = null;
    private StringBuilder builder;
    private int index;
    private KitDocumentContent content;

    @Override
    public void characters(char[] ch, int start, int length) {
        if(kit == null){
            builder = new StringBuilder();
        }else {
            builder.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() {
        content = new KitDocumentContent();
    }

    public KitDocumentContent getContent() {
        return content;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case LINE_RES_WS:
                kit = new Kit();
                index = 0;
                break;
            case VALUE:
                builder = new StringBuilder();
                index++;
                break;
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case LINE_RES_WS:
                this.content.addKit(kit);
                break;

            case VALUE:
                if (index == 1){
                    kit.setLabel(builder.toString());
                }
                if (index == 2){
                    kit.setKitStatus(builder.toString());
                }
                if (index == 3){
                    kit.setClientCode(builder.toString());
                }
                if (index == 4){
                    kit.setSocialReason(builder.toString());
                }

                break;
        }
    }

}