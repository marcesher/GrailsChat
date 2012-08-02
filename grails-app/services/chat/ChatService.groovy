package chat

import org.atmosphere.cpr.*


class ChatService {

    static transactional = false
    static atmosphere = [mapping: '/atmosphere/chatty']

    def onRequest = { event ->
        println "Inside onRequest!"
        try{
            AtmosphereRequest req = event.request
            println req
            println req.method
            if( req.method.equalsIgnoreCase("GET") ){
                println 'Suspending'
                event.suspend()
            } else if ( req.method.equalsIgnoreCase("POST") ){
                String stuff = req.reader.readLine().trim()
                println "Stuff is $stuff"
                event.broadcaster.broadcast(stuff)
            }
        } catch( Exception e ){
            println "ERROR!!!!!"
        }

    }

    def onStateChange = { event ->
        println "Inside onStateChange!"
        AtmosphereResource r = event.resource
        AtmosphereResponse res = r.response

        if( event.isSuspended() ){
            String author = body.substring(body.indexOf(":") + 2, body.indexOf(",") - 1);
            String message = body.substring(body.lastIndexOf(":") + 2, body.length() - 2);
            res.writer.write()
        }
    }
}
