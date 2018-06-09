package com.redhat.opendemos.view;

import com.redhat.opendemos.model.Session;

import java.util.ArrayList;
import java.util.List;

public class DefaultView {

    private List<Session> SessionList = new ArrayList<Session>();

    public List<Session>getSessionList(){ return this.SessionList;}

}