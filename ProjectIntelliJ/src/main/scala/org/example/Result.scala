package org.example


import org.apache.spark.sql.Row


object Result {

  case class result(ans: String)

  def answerresult(arg: Array[Row]): result = {
    val datos = arg.map(row=> row.mkString(","))
    val ans= datos.mkString("\n")
    result(ans)
  }

}
