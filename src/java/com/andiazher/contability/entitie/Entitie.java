/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andiazher.contability.entitie;


import com.andiazher.contability.app.App;
import com.andiazher.contability.controller.JSONA;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author andre
 */
public final class Entitie{
    
    private String name;
    private String id;
    private Map<String, Object > data;
    private JSONA json;

    public Entitie() {
        setData(new HashMap<>());
        setName("PROTOTIPE");
        setId("0");
        setJson(new JSONA() );
    }
    
    
    public Entitie(String name)  {
        setData(new HashMap<>());
        setName(name);
        setId("0");
        setJson(new JSONA() );
        String sql = "";
        sql+="SELECT COLUMN_NAME FROM "+App.getConnectionMysql().getInformationSchema()+".columns "
                + "where table_schema ='"+App.getConnectionMysql().getDb()+"' and table_name='"+name+"'";
        try{
            ResultSet resultSet = App.consult(sql);
            while(resultSet.next()){
                String request = resultSet.getString("COLUMN_NAME");
                if(!request.equals("id")){
                    data.put(request, "");
                }
            }
        }
        catch(SQLException s){
            System.out.println(s);
        }
        
    }

    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONA getJson() {
        json.setObject(data);
        return json;
    }

    public void setJson(JSONA json) {
        this.json = json;
        this.json.setObject(data);
    }
    
    

    public ArrayList<String> getColums() {
        ArrayList<String> colums = new ArrayList<>();
        for(Map.Entry<String, Object> j: data.entrySet()){
            colums.add(j.getKey());
        }
        return colums;
    }

    
    public ArrayList<String> getData() {
        ArrayList<String> datos = new ArrayList<>();
        for(Map.Entry<String, Object> j: data.entrySet()){
            datos.add(j.getValue().toString());
        }
        return datos;
    }

    

    public Map<String, Object> getDataMap() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
    
    
    /**
     * 
     * @return verdadero si la operacion es un exito.
     * @throws SQLException 
     */
    public boolean create() throws SQLException{
        String sql = "";
        String columnas="",datos="";
        String k = "";
        for(Map.Entry<String, Object> j: data.entrySet()){
            if(!j.getKey().equals("id") || !j.getKey().equals("ID") || !j.getKey().equals("Id") ){
                columnas+= k + j.getKey()+"";
                datos+= k + "'"+j.getValue()+"'";
                k = ",";
            }
        }
        sql+="insert into "+App.getConnectionMysql().getDb()+"."+name+" ("+columnas+")  values ("+datos+");";
        return App.update(sql);
    }
    /**
     * 
     * @return verdadero si la operacion es un exito
     * @throws SQLException 
     */
    public boolean update() throws SQLException{
        String sql = "";
        String datos="";
        String k = "";
        for(Map.Entry<String, Object> j: data.entrySet()){
            datos+= k +" "+ j.getKey()+"='"+j.getValue()+"'";
            k = ",";
        }
        sql+="update "+App.getConnectionMysql().getDb()+"."+name+" set "+datos+" where id = "+id;
        return App.update(sql);
    }
    /**
     * 
     * @return verdadero si la operacion es un exito
     * @throws SQLException 
     */
    public boolean delete() throws SQLException{
        String sql = "";
        sql+="delete from "+App.getConnectionMysql().getDb()+"."+name+ " where id = "+id;
        return App.update(sql);
    }
    
    /**
     * 
     * @param id
     * @throws SQLException 
     */
    public void getEntitieId(String id) throws SQLException{
        String sql = "";
        sql+="SELECT * FROM "+App.getConnectionMysql().getDb()+"."+ name +" where id="+id ;
        ResultSet query= App.consult(sql);
        setId(id);
        while(query.next()){
            for(Map.Entry<String, Object> j: data.entrySet()){
                data.put( j.getKey(), query.getString( j.getKey() ));
            }
        }
    }
    
    /**
     * 
     * @param param
     * @param param2
     * @return
     * @throws SQLException 
     */
    public ArrayList<Entitie> getEntitieParams(ArrayList<String> param, ArrayList<String> param2) throws SQLException{
        String sql = "";
        String params = "";
        for(int i=0; i<param.size();i++){
            if(i!=param.size()-1){
                params+=param.get(i)+"='"+param2.get(i)+"' and ";
            }
            else{
                params+=param.get(i)+"='"+param2.get(i)+"'";
            }
        }
        sql+="SELECT * FROM "+App.getConnectionMysql().getDb()+"."+ name +" where "+params+"";
        ResultSet query= App.consult(sql);
        ArrayList<Entitie> entities= new ArrayList<>();
        while(query.next()){
            Entitie entitie = new Entitie(name);
            entitie.setId(query.getString(name+".id"));
            for(Map.Entry<String, Object> j: data.entrySet()){
                entitie.getDataMap().put( j.getKey(), query.getString( j.getKey() ));
            }
            entitie.getDataMap().put( "id",  entitie.getId() );
            entities.add(entitie);
        }
        return entities;
    }
    /**
     * 
     * @param param
     * @param param2
     * @param opera
     * @return
     * @throws SQLException 
     */
    public ArrayList<Entitie> getEntitieParams(ArrayList<String> param, ArrayList<String> param2, ArrayList<String> opera ) throws SQLException{
        String sql = "";
        String params = "";
        for(int i=0; i<param.size();i++){
            if(i!=param.size()-1){
                params+=param.get(i)+ " "+opera.get(i)+"'"+param2.get(i)+"' and ";
            }
            else{
                params+=param.get(i)+ " "+opera.get(i)+"'"+param2.get(i)+"'";
            }
        }
        sql+="SELECT * FROM "+App.getConnectionMysql().getDb()+"."+ name +" where "+params+"";
        ResultSet query= App.consult(sql);
        ArrayList<Entitie> entities= new ArrayList<>();
        while(query.next()){
            Entitie entitie = new Entitie(name);
            entitie.setId(query.getString(name+".id"));
            for(Map.Entry<String, Object> j: data.entrySet()){
                entitie.getDataMap().put( j.getKey(), query.getString( j.getKey() ));
            }
            entitie.getDataMap().put( "id",  entitie.getId() );
            entities.add(entitie);
        }
        return entities;
    }
    /**
     * 
     * @param param
     * @param param2
     * @param opera
     * @param sqlq
     * @return
     * @throws SQLException 
     */
    public ArrayList<Entitie> getEntitieParams(ArrayList<String> param, ArrayList<String> param2, ArrayList<String> opera, String sqlq, String Otables ) throws SQLException{
        String sql = "SELECT "+name+".id, ";
        String k = "";
        for(Map.Entry<String, Object> j: data.entrySet()){
            sql+= k + " " + name + "." + j.getKey();
            k = ",";
        }
        String params = "";
        for(int i=0; i<param.size();i++){
            if(i!=param.size()-1){
                params+=name+"."+param.get(i)+ " "+opera.get(i)+"'"+param2.get(i)+"' and ";
            }
            else{
                params+=name+"."+param.get(i)+ " "+opera.get(i)+"'"+param2.get(i)+"'";
            }
        }
        params+=sqlq;
        if(!Otables.equals("")){
            Otables = ", "+Otables;
        }
        sql+=" FROM "+App.getConnectionMysql().getDb()+"."+ name +Otables+"  where "+params+"";
        ResultSet query= App.consult(sql);
        ArrayList<Entitie> entities= new ArrayList<>();
        while(query.next()){
            Entitie entitie = new Entitie(name);
            entitie.setId(query.getString(name+".id"));
            for(Map.Entry<String, Object> j: data.entrySet()){
                entitie.getDataMap().put( j.getKey(), query.getString( j.getKey() ));
            }
            entitie.getDataMap().put( "id",  entitie.getId() );
            entities.add(entitie);
        }
        return entities;
    }
    
    /**
     * 
     * @param param
     * @param param2
     * @return
     * @throws SQLException 
     */
    public ArrayList<Entitie> getEntitieParam(String param, String param2) throws SQLException{
        String sql = "";
        String params = "";
        params+=param+"='"+param2+"'";
        sql+="SELECT * FROM "+App.getConnectionMysql().getDb()+"."+ name +" where "+params+"";
        ResultSet query= App.consult(sql);
        ArrayList<Entitie> entities= new ArrayList<>();
        while(query.next()){
            Entitie entitie = new Entitie(name);
            entitie.setId(query.getString("id"));
            for(Map.Entry<String, Object> j: data.entrySet()){
                entitie.getDataMap().put( j.getKey(), query.getString( j.getKey() ));
            }
            entitie.getDataMap().put( "id",  entitie.getId() );
            entities.add(entitie);
        }
        return entities;
    }
    
    /**
     * 
     * @return TODAS LAS ENTidADES
     * @throws SQLException 
     */
    public ArrayList<Entitie> getEntities() throws SQLException{
        String sql = "";
        sql+="SELECT * FROM "+App.getConnectionMysql().getDb()+"."+ name + " order by id";
        ResultSet query= App.consult(sql);
        ArrayList<Entitie> entities= new ArrayList<>();
        while(query.next()){
            Entitie entitie = new Entitie(name);
            entitie.setId(query.getString("id"));
            for(Map.Entry<String, Object> j: data.entrySet()){
                entitie.getDataMap().put( j.getKey(), query.getString( j.getKey() ));
            }
            entitie.getDataMap().put( "id",  entitie.getId() );
            entities.add(entitie);
        }
        return entities;
    }
    
    /**
     * 
     * @return TOSTRING ENTITIE
     */
    
    @Override
    public String toString() {
        return "Entitie{" + "name=" + name + ", id=" + id + ", colums=" + getColums() + ", data=" + getData() + '}';
    }
    
    /**
     * 
     * @param param
     * @return DATO DE ACUERDO A LA COLUMANA INGRESADA EN @param
     */
    public Object getDataOfLabel(String param){
        for(Map.Entry<String, Object> j: data.entrySet()){
            if(j.getKey().equals(param)){
                return j.getValue();
            }
        }
        return null;
    }

    public ArrayList<Entitie> getEntitieParam(String param, String param2, String param3) throws SQLException {
        String sql = "";
        String params = "";
        params+=param+"='"+param2+"'";
        sql+="SELECT * FROM "+App.getConnectionMysql().getDb()+"."+ name +" where "+params+" order by "+param3;
        ResultSet query= App.consult(sql);
        ArrayList<Entitie> entities= new ArrayList<>();
        
        while(query.next()){
            Entitie entitie = new Entitie(name);
            entitie.setId(query.getString("id"));
            for(Map.Entry<String, Object> j: data.entrySet()){
                entitie.getDataMap().put( j.getKey(), query.getString( j.getKey() ));
            }
            entitie.getDataMap().put( "id",  entitie.getId() );
            entities.add(entitie);
        }
        return entities;
    }

}
