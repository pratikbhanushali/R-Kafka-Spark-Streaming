
import kafka.serializer.StringDecoder

import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf

object DirectKafkaStream {


  def main(args: Array[String]) {
    if (args.length < 2) {
      System.err.println("Please specify args as Broker-list and topic")
      System.exit(1)
    }


    val Array(brokers, topics) = args

    // Create context with 2 second batch interval
    val sparkConf = new SparkConf().setMaster("local").setAppName("DirectKafkaWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(8))

    // Create direct kafka stream with brokers and topics
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams,
      topicsSet)

    messages.print()

    ssc.start()
    ssc.awaitTermination()
  }

}
