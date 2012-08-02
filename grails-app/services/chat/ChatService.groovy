package chat

import org.atmosphere.cpr.*
import org.atmosphere.cpr.AtmosphereResource.TRANSPORT

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

        try{
            if( event.isSuspended() ){
                String body = event.message.toString()
                String author = body.substring(body.indexOf(":") + 2, body.indexOf(",") - 1);
                String message = body.substring(body.lastIndexOf(":") + 2, body.length() - 2);
                String data = new Data(author: author, text: message).toString()
                res.writer.write( data )

                switch( r.transport() ){
                    case TRANSPORT.JSONP:
                    case TRANSPORT.LONG_POLLING:
                        println "Resuming with data $data"
                        event.resource.resume()
                        break
                    default:
                        println "Flushing $data"
                        res.writer.flush()
                }
            } else if( !event.isResuming() ){
                event.broadcaster().broadcast( new Data(author: 'Someone', text: 'buh bye').toString() )
            }
        } catch( Exception e ){
            println "ERROR in onStateChange: $e"
        }

    }
}
