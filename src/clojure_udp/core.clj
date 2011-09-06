(ns clojure-udp.core
  (:import (java.net DatagramPacket DatagramSocket InetAddress)))

(defn make-socket 
	([] (new DatagramSocket))
	([port] (new DatagramSocket port)))
	
(defn send-data [send-socket ip port data]
  (let [ipaddress (InetAddress/getByName ip),
        send-packet (new DatagramPacket (.getBytes data) (.length data) ipaddress port)]
  (.send send-socket send-packet)))

(defn receive-data [receive-socket]
  (let [receive-data (byte-array 1024),
       receive-packet (new DatagramPacket receive-data 1024)]
  (.receive receive-socket receive-packet)
  (new String (.getData receive-packet) 0 (.getLength receive-packet))))

(defn make-receive [receive-port]
	(let [receive-socket (make-socket receive-port)]
		(fn [] (receive-data receive-socket))))

(defn make-send [ip port]
	(let [send-socket (make-socket)]
	     (fn [data] (send-data send-socket ip port data))))

