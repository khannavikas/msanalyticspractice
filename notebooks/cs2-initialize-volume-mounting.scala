// Databricks notebook source
import scala.util.control._

// COMMAND ----------

 val configs = Map(
  "fs.azure.account.auth.type" -> "OAuth",
  "fs.azure.account.oauth.provider.type" -> "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider",
  "fs.azure.account.oauth2.client.id" -> "29f55893-424f-4030-b8f6-2d4b7f20d7b0",
  "fs.azure.account.oauth2.client.secret" -> "1Rb-_2yhR2NOMcs.evKs5q1d9m~1b20GI.",
  "fs.azure.account.oauth2.client.endpoint" -> "https://login.microsoftonline.com/82e453a4-e35f-4a61-8902-1e5887e94115/oauth2/token")


// COMMAND ----------

val mounts = dbutils.fs.mounts()
val mountPath = "/mnt/data"
var isExist: Boolean = false
val outer = new Breaks;

outer.breakable {
  for(mount <- mounts) {
    if(mount.mountPoint == mountPath) {
      isExist = true;
      outer.break;
    }
  }
}

if(isExist) {
  println("Volume Mounting for Case Study Data Already Exist!")
}
else {
  dbutils.fs.mount(
    source = "abfss://casestudydata@iomegadls.dfs.core.windows.net/",
    mountPoint = "/mnt/data",
    extraConfigs = configs)
}