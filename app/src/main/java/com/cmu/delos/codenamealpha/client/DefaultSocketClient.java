//package com.cmu.delos.codenamealpha.client;
//import java.io.*;
//import java.net.*;
///**
// * Created by felixamoruwa on 7/22/15.
// */
//public class DefaultSocketClient extends Thread implements SocketClientConstants, SocketClientInterface {
//
//    private static final boolean DEBUG = true;
//    private BufferedReader reader;
//    private BufferedWriter writer;
//    private Socket sock;
//    private String strHost;
//    private int iPort;
//    private boolean isConnected = false;
//
//    // int iSMTP_PORT = 4400; //Sample
//
//
//    public DefaultSocketClient(String strHost, int iPort) {
//        setPort (iPort);
//        setHost (strHost);
//    }//constructor
//
//    public DefaultSocketClient(Socket clientSocket) {
//
//    }
//
//    public void run(){
//        if (openConnection()){
//            handleSession();
//            closeSession();
//        }
//    }//run
//
//    public boolean openConnection(){
//
//        try {
//            sock = new Socket(strHost, iPort);
//        }
//        catch(IOException socketError){
//            if (DEBUG) {
//                System.err.println("Unable to connect to " + strHost);
//            }
//            return false;
//        }
//        try {
//            writer = new BufferedWriter
//                    (new OutputStreamWriter (sock.getOutputStream()));
//            reader = new BufferedReader
//                    (new InputStreamReader(sock.getInputStream()));
//
//        }
//        catch (Exception e){
//            if (DEBUG) {
//                System.err.println("Unable to obtain stream to/from " + strHost);
//            }
//            return false;
//        }
//        return true;
//    }
//
//    public void handleSession(){
//        String strInput = "";
//        if (DEBUG){
//            System.out.println ("Handling session with " + strHost + ":" + iPort);
//        }
//        try {
//            while ((strInput = reader.readLine()) != null) {
//                handleInput(strInput);
//            }
//        }
//        catch (IOException e){
//            if (DEBUG){
//                System.out.println ("Handling session with " + strHost + ":" + iPort);
//            }
//        }
//    }
//
//    public void sendOutput(String strOutput){
//        try {
//            writer.write(strOutput, 0, strOutput.length());
//        }
//        catch (IOException e){
//            if (DEBUG){
//                System.out.println("Error writing to " + strHost);
//            }
//        }
//    }
//
//    public void handleInput(String strInput){
//        System.out.println(strInput);
//    }
//
//    public void closeSession(){
//        try {
//            writer = null;
//            reader = null;
//            sock.close();
//        }
//        catch (IOException e){
//            if (DEBUG) {
//                System.err.println("Error closing socket to " + strHost);
//            }
//        }
//    }
//
//    public void setHost(String strHost){
//        this.strHost = strHost;
//    }
//
//    public void setPort(int iPort){
//        this.iPort = iPort;
//    }
//
//    public static void main (String arg[]) throws IOException, ClassNotFoundException {
//
//    }
//}
