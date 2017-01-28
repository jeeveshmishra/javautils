package my.utils;

import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static org.testng.Assert.*;

public class XmlUtilsTest {
    private final static String CONTENT_NAME = "BehandlerKrav";
    private final static String CONTENT_NAMESPACE = "http://www.kith.no/xmlstds/bkm/2006-12-20";
    private final static String XML_NAMESPACE = "http://www.kith.no/xmlstds/msghead/2006-05-24";

    private String xml =
            "<MsgHead xmlns=\"http://www.kith.no/xmlstds/msghead/2006-05-24\">\n" +
            "  <MsgInfo>\n" +
            "    <Type DN=\"Legeoppgjørsmelding\" V=\"LOM\"/>\n" +
            "    <MIGversion>v1.2 2006-05-24</MIGversion>\n" +
            "    <GenDate>2016-09-26T13:43:19.7652193+02:00</GenDate>\n" +
            "    <MsgId>6cb3c665-fc1b-4687-a9ed-2909d4763907</MsgId>\n" +
            "    <Ack DN=\"Ja\" V=\"J\"/>\n" +
            "    <Sender>\n" +
            "      <Organisation>\n" +
            "        <OrganisationName>ServiceCenterName1_bbffcf9d-e294-4112-b993-3e1d500e7701</OrganisationName>\n" +
            "        <Ident>\n" +
            "          <Id>1</Id>\n" +
            "          <TypeId DN=\"Organisasjonsnummeret i Enhetsregister\" S=\"2.16.578.1.12.4.1.1.9051\" V=\"ENH\"/>\n" +
            "        </Ident>\n" +
            "        <HealthcareProfessional>\n" +
            "          <Ident>\n" +
            "            <Id>92160</Id>\n" +
            "            <TypeId DN=\"Fødselsnummer\" S=\"2.16.578.1.12.4.1.1.8116\" V=\"FNR\"/>\n" +
            "          </Ident>\n" +
            "          <Ident>\n" +
            "            <Id>92160</Id>\n" +
            "            <TypeId DN=\"Identifikator fra Helsetjenesteenhetsregisteret\" S=\"2.16.578.1.12.4.1.1.8116\" V=\"HER\"/>\n" +
            "          </Ident>\n" +
            "        </HealthcareProfessional>\n" +
            "      </Organisation>\n" +
            "    </Sender>\n" +
            "    <Receiver>\n" +
            "      <Organisation>\n" +
            "        <OrganisationName>NAV</OrganisationName>\n" +
            "        <Ident>\n" +
            "          <Id>889640782</Id>\n" +
            "          <TypeId DN=\"Organisasjonsnummeret i Enhetsregister\" S=\"2.16.578.1.12.4.1.1.9051\" V=\"ENH\"/>\n" +
            "        </Ident>\n" +
            "        <Ident>\n" +
            "          <Id>93972</Id>\n" +
            "          <TypeId DN=\"Identifikator fra Helsetjenesteenhetsregisteret\" S=\"2.16.578.1.12.4.1.1.9051\" V=\"HER\"/>\n" +
            "        </Ident>\n" +
            "      </Organisation>\n" +
            "    </Receiver>\n" +
            "  </MsgInfo>\n" +
            "  <Document>\n" +
            "    <RefDoc>\n" +
            "      <MsgType V=\"XML\"/>\n" +
            "      <MimeType>text/xml</MimeType>\n" +
            "      <Description>BehandlerKrav</Description>\n" +
            "      <Content>\n" +
            "        <BehandlerKrav navnEPJ=\"Melin Medical Health Economy Module\" versjonEPJ=\"1.0\" xmlns=\"http://www.kith.no/xmlstds/bkm/2006-12-20\">\n" +
            "          <Konto>1324-1</Konto>\n" +
            "          <Enkeltregning>\n" +
            "            <Guid>954c783a-78e9-4c0d-a118-2deb09a4f372</Guid>\n" +
            "            <BetaltEgenandel erBetalt=\"false\" korrigering=\"false\"/>\n" +
            "            <RegningNr>1</RegningNr>\n" +
            "            <DatoTid>2016-09-26T00:00:00</DatoTid>\n" +
            "            <Patient>\n" +
            "              <FamilyName>Williamson</FamilyName>\n" +
            "              <GivenName>Ericka</GivenName>\n" +
            "              <Ident>\n" +
            "                <Id>12037647657</Id>\n" +
            "                <TypeId DN=\"Fødselsnummer\" S=\"2.16.578.1.12.4.1.1.8116\" V=\"FNR\"/>\n" +
            "              </Ident>\n" +
            "              <ArbeidstagerNorge DN=\"Ja\" V=\"J\"/>\n" +
            "            </Patient>\n" +
            "            <Behandling>\n" +
            "              <Diagnose S=\"2.16.578.1.12.4.1.1.7110\" V=\"K024\"/>\n" +
            "              <Diagnose S=\"2.16.578.1.12.4.1.1.7170\" V=\"B80\"/>\n" +
            "              <Takst>\n" +
            "                <Verdi U=\"NOK\" V=\"7\"/>\n" +
            "                <Kode>1e</Kode>\n" +
            "                <Antall>1</Antall>\n" +
            "              </Takst>\n" +
            "              <SumKrav>\n" +
            "                <EgenandelHonorar U=\"NOK\" V=\"-50\"/>\n" +
            "                <Refusjon U=\"NOK\" V=\"7\"/>\n" +
            "              </SumKrav>\n" +
            "            </Behandling>\n" +
            "          </Enkeltregning>\n" +
            "          <Krav>\n" +
            "            <AntallRegninger>1</AntallRegninger>\n" +
            "            <SumKravSamlet U=\"NOK\" V=\"7\"/>\n" +
            "          </Krav>\n" +
            "        </BehandlerKrav>\n" +
            "      </Content>\n" +
            "    </RefDoc>\n" +
            "  </Document>\n" +
            "</MsgHead>";

    @Test
    public void test_getNamespace() throws Exception {
        String namespace = XmlUtils.getNamespace(xml);
        assertEquals(namespace, XML_NAMESPACE, "Namespace is not as expected");
    }

    @Test
    public void test_getNode() throws Exception {
        Node node = XmlUtils.getNode(xml, CONTENT_NAMESPACE, CONTENT_NAME);

        String nodeXml = XmlUtils.toXml(node);
        String namespace = XmlUtils.getNamespace(nodeXml);

        assertTrue(nodeXml.contains(CONTENT_NAME), "Expected element does not exists");
        assertEquals(namespace, CONTENT_NAMESPACE, "Root namespace is not as expected");
    }

    @Test
    public void test_getNode_withoutNamespace() throws Exception {
        Node node = XmlUtils.getNode(xml, CONTENT_NAME);

        String nodeXml = XmlUtils.toXml(node);
        String namespace = XmlUtils.getNamespace(nodeXml);

        assertTrue(nodeXml.contains(CONTENT_NAME), "Expected element does not exists");
        assertEquals(namespace, CONTENT_NAMESPACE, "Root namespace is not as expected");
    }

    @Test
    public void test_getFirstChildElement() throws Exception {
        Node node = XmlUtils.getNode(xml, "Content");
        Element child = XmlUtils.getFirstChildElement(node);

        String nodeXml = XmlUtils.toXml(child);
        String namespace = XmlUtils.getNamespace(nodeXml);

        assertTrue(nodeXml.contains(CONTENT_NAME), "Expected element does not exists");
        assertEquals(namespace, CONTENT_NAMESPACE, "Root namespace is not as expected");
    }

    @Test
    public void test_getElementXml() throws Exception {
        String contentXml = XmlUtils.getElementXml(xml, CONTENT_NAMESPACE, CONTENT_NAME);
        String namespace = XmlUtils.getNamespace(contentXml);

        assertTrue(contentXml.contains(CONTENT_NAME), "Expected element does not exists");
        assertEquals(namespace, CONTENT_NAMESPACE, "Root namespace is not as expected");
    }

    @Test
    public void test_getElementXml_withoutNamespace() throws Exception {
        String contentXml = XmlUtils.getElementXml(xml, CONTENT_NAME);
        String namespace = XmlUtils.getNamespace(contentXml);

        assertTrue(contentXml.contains(CONTENT_NAME), "Expected element does not exists");
        assertEquals(namespace, CONTENT_NAMESPACE, "Root namespace is not as expected");
    }
}