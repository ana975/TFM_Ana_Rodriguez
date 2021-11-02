package org.example


import org.apache.spark.sql.Row

object Result {

  def result(arg: Array[Row]): String = {
    val rlt = arg.mkString("\n")
    rlt.mkString(",")
  }

}
