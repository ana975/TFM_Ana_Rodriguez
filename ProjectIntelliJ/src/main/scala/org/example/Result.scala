package org.example


import org.apache.spark.sql.Row

object Result {

  case class result(arg: Array[Row])

  def answerresult(arg: Array[Row]): String = {
    val datos = arg.map(row=> row.mkString(","))
    datos.mkString("\n")
  }

}
