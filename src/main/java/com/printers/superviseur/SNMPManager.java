package com.printers.superviseur;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.*;

import java.io.IOException;
import java.util.*;

public class SNMPManager {

    Snmp snmp = null;
    String address = null;

    /**
     * Constructor
     *
     * @param add
     */
    public SNMPManager(String add) {
        address = add;
    }

/*    public static void main(String[] args) throws IOException {
        SNMPManager client = new SNMPManager("udp:172.16.103.163/161");
        try
        {
            client.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        */

    /**
     * OID - .1.3.6.1.2.1.1.1.0 => SysDec
     * OID - .1.3.6.1.2.1.1.5.0 => SysName
     * => MIB explorer will be usefull here, as discussed in previous article
     *//*     //String sysDescr = client.getAsString(new OID("1.3.6.1.2.1.2.2"));

        List<Map<String,String>> sysDescr = client.getTableAsStrings(IntermecPrinter.IntermecPrinterListIOD());
        System.out.print(sysDescr);
    }*/
    public List<Map<String, String>> getSNMP() {
        /**
         * Port 161 is used for Read and Other operations
         * Port 162 is used for the trap generation
         */
        SNMPManager client = new SNMPManager("udp:" + address + "/161");
        try {
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * OID - .1.3.6.1.2.1.1.1.0 => SysDec
         * OID - .1.3.6.1.2.1.1.5.0 => SysName
         * => MIB explorer will be usefull here, as discussed in previous article
         */     //String sysDescr = client.getAsString(new OID("1.3.6.1.2.1.2.2"));

        List<Map<String, String>> sysDescr = client.getTableAsStrings(IntermecPrinter.IntermecPrinterListIOD());

        return sysDescr;
    }

    /*public static OID[] iodlist()
    {
        OID me = new OID();
        List<OID> listiod = new ArrayList<>();
        Iterator it = IntermecPrinter.IntermecPrinterListDetails().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry val = (Map.Entry)it.next();
            listiod.add(new OID(val.getValue().toString()));
        }
        return listiod;
    }*/

    /**
     * Start the Snmp session. If you forget the listen() method you will not
     * get any answers because the communication is asynchronous
     * and the listen() method listens for answers.
     *
     * @throws IOException
     */
    private void start() throws IOException {
        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
// Do not forget this line!
        transport.listen();
    }

    /**
     * Method which takes a single OID and returns the response from the agent as a String.
     *
     * @param oid
     * @return
     * @throws IOException
     */
    public String getAsString(OID oid) throws IOException {
        ResponseEvent event = get(new OID[]{oid});
        return event.getResponse().get(0).getVariable().toString();
    }

    /**
     * This method is capable of handling multiple OIDs
     *
     * @param oids
     * @return
     * @throws IOException
     */
    public ResponseEvent get(OID oids[]) throws IOException {
        PDU pdu = new PDU();
        for (OID oid : oids) {
            pdu.add(new VariableBinding(oid));
        }
        pdu.setType(PDU.GET);
        ResponseEvent event = snmp.send(pdu, getTarget(), null);
        if (event != null) {
            return event;
        }
        throw new RuntimeException("GET timed out");
    }

    /**
     * This method returns a Target, which contains information about
     * where the data should be fetched and how.
     *
     * @return
     */
    private Target getTarget() {
        Address targetAddress = GenericAddress.parse(address);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(2500);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }

    public List<Map<String, String>> getTableAsStrings(OID[] oids) {
        TableUtils tUtils = new TableUtils(snmp, new DefaultPDUFactory());

        List<TableEvent> events = tUtils
                .getTable(getTarget(), oids, null, null);

        List<Map<String, String>> list = new ArrayList<>();
        for (TableEvent event : events) {
            if (event.isError()) {
                System.err.println(event);
                continue;
            }

            Map<String, String> strList = new HashMap<String, String>();
            list.add(strList);
            for (VariableBinding vb : event.getColumns()) {

                strList.put(vb.getOid().toString(), vb.toValueString());
            }
        }
        return list;
    }

}
