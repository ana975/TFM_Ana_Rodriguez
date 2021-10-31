package org.example


import org.apache.spark
import org.apache.spark.sql.Row

object Result {

  def result(arg: Array[Row]): String = {
    val myDF= concat_ws(",",arg)
  }

}
