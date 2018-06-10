package APIs;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Clase padre desde donde se extienden el resto. Sirve principalmente para asignar/obtener propiedades básicas
 * a la hora de realizar una petición (clave, sensor, idioma, etc.) y para obtener un registro de todas las peticiones realizadas
 * @author Luis Marcos
 */
public abstract class MapsJava {

    //request properties 
    private static int connectTimeout=300;
    private static String region="es";
    private static String language="es";
    private static Boolean sensor= Boolean.FALSE;
    private static String APIKey = "AIzaSyCdnsMrGYyJ8LCwa0QVY8YZGc2kI8WRIgA";
    
    //Stock request
    private static String[][] stockRequest=new String[0][6];

     protected ArrayList<String> getNodesString(NodeList node){
         ArrayList<String> result=new ArrayList<>();
             for (int j = 0, n = node.getLength(); j < n; j++) {
                String nodeString = node.item(j).getTextContent();
                result.add(nodeString);
             }
        return result;
    }
     
    protected ArrayList<Double> getNodesDouble(NodeList node){
         ArrayList<Double> result=new ArrayList<>();
             for (int j = 0, n = node.getLength(); j < n; j++) {
                String nodeString = node.item(j).getTextContent();
                result.add(Double.valueOf(nodeString));
             }
        return result;
    }
    
    protected ArrayList<Integer> getNodesInteger(NodeList node){
         ArrayList<Integer> result=new ArrayList<>();
             for (int j = 0, n = node.getLength(); j < n; j++) {
                String nodeString = node.item(j).getTextContent();
                result.add(Integer.valueOf(nodeString));
             }
        return result;
    }
    
    /**
    * Author: Christian d'Heureuse (www.source-code.biz, www.inventec.ch/chdh)
    * Reallocates an array with a new size, and copies the contents
    * of the old array to the new array.
    * @param oldArray  the old array, to be reallocated.
    * @param newSize   the new array size.
    * @return          A new array with the same contents.
    */
    protected Object resizeArray (Object oldArray, int newSize) {
       int oldSize = java.lang.reflect.Array.getLength(oldArray);
       Class elementType = oldArray.getClass().getComponentType();
       Object newArray = java.lang.reflect.Array.newInstance(
             elementType, newSize);
       int preserveLength = Math.min(oldSize, newSize);
       if (preserveLength > 0)
          System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
       return newArray; 
    }
    
    //Public methods
    /**
     * Comprueba si la clave de desarrollador API Google Maps es válida.
     * @param key clave de desarrollador API Google Maps
     * @return devuelve el estado de una petición con clave API. En caso de ser válida, devuelve
     * "OK", en cualquier otro caso la clave no es correcta.
     * @see MapsJava#setKey(java.lang.String) 
     * @see MapsJava#getKey() 
     */
    public static String APIkeyCheck(String key){
        try{
            URL url=new URL("https://maps.googleapis.com/maps/api/place/search/xml?location=0,0&radius=1000" + 
                    "&sensor=false&key=" + key);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
            DocumentBuilder builder = factory.newDocumentBuilder(); 
            Document document = builder.parse(url.openStream()); 
            XPathFactory xpathFactory = XPathFactory.newInstance(); 
            XPath xpath = xpathFactory.newXPath(); 

            NodeList nodeLatLng = (NodeList) xpath.evaluate("PlaceSearchResponse/status", 
                            document, XPathConstants.NODESET);
            String status = nodeLatLng.item(0).getTextContent();
            return status;
        }catch (Exception e){
            return "NO STATUS";
        }
    }
    
    /**
     * Obtiene la clave actual de desarrollador de API Google Maps (sólo necesario para Places)
     * @return obtiene string con clave actual
     * @see MapsJava#setKey(java.lang.String) 
     * @see MapsJava#APIkeyCheck(java.lang.String) 
     */
    public static String getKey() {
        return APIKey;
    }

    /**
     * Establece clave de desarrollador API Google Maps (sólo necesario para Places)
     * @param aKey string con clave API de desarrollador
     * @see MapsJava#getKey() 
     * @see MapsJava#APIkeyCheck(java.lang.String) 
     */
    public static void setKey(String aKey) {
        APIKey = aKey;
    }
}
    
    
    