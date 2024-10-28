
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.*;



public class interview {

    //Scenario 1: Validate the block information and ensure that there are a total of 2875 transactions in the given block

  //  endpoints
 // String hash;
  @BeforeTest
  public String getHash(){
      baseURI="https://blockstream.info/api";
      String hash;
      Response res1= given().log().all().when().get("/block-height/680000").then().extract().response();
      //System.out.println(test1.getBody().asString());
      hash = res1.getBody().asString();
      return  hash;
  }

   @Test(priority = 1)
    public void testCaseOne(){
       baseURI="https://blockstream.info/api";
      Response getBlockInfo = given().log().all().when().get("https://blockstream.info/api/block/"+getHash()).then().extract().response();

       JSONObject json= new JSONObject(getBlockInfo.getBody().asString());
       int transactionCount = json.getInt("tx_count");
       System.out.println(transactionCount);

       Assert.assertEquals(transactionCount,2875);
       System.out.println("test case 1 passed");
   }
   @Test(priority = 2)
   public void testCaseTwo(){

       int startIndex=0;
       int endIndex= 24;

       int i=startIndex;
       Set<String> txs= new HashSet<>();

       while(i<=2850){
           baseURI="https://blockstream.info/api";
           Response res = given().when().get("/block/" + getHash() +"/txs/"+startIndex);

               for(int k =0;k<24;k++) {
                   JSONArray json= new JSONArray(res.getBody().asString());
                   String txid= json.getJSONObject(k).get("txid").toString();
                   System.out.println(txid);
                   if(txid.equals("96d92f03000f625a38bf8cb91c01188a02b7972238cc6c4e0c6f334cf755004d")|| txid.equals("6dd68336c085d5b7b694e2bf6f6c11bca589aea07b6f1c0232bd627c3d217074")){
                       txs.add(txid);
                      if(txs.size()==2) {
                          break;
                      }
                   }
               }
               startIndex=startIndex+25;
               endIndex=endIndex+25;
               i=startIndex;
       }
       Assert.assertEquals(txs.size(),2);

       System.out.println(txs.size());
       for(String x:txs){
           System.out.println(x);
       }




   }}
