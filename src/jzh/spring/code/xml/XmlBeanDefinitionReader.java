package xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import base.BeanDefinition;
import base.BeanReference;
import base.PropertyValue;

public class XmlBeanDefinitionReader {
	private Map<String, BeanDefinition> registry = new HashMap<String, BeanDefinition>();
	private InputStream inputStream;
	
	public XmlBeanDefinitionReader(String location) throws IOException{
		URL url = this.getClass().getClassLoader().getResource(location);
		URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        inputStream = urlConnection.getInputStream();
	}
	
	public void loadBeanDefinitions() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(inputStream);
		Element root = doc.getDocumentElement();
		parseBeanDefinitions(root);
		inputStream.close();
	}
	
	public void parseBeanDefinitions(Element root) {
		NodeList nl = root.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				processBeanDefinition(ele);
			}
		}
	}
	
	public void processBeanDefinition(Element ele) {
		String name = ele.getAttribute("name");
		String className = ele.getAttribute("class");
		BeanDefinition beanDefinition = new BeanDefinition();
		beanDefinition.setBeanClassName(className);
		processProperty(ele,beanDefinition);
		registry.put(name, beanDefinition);
	}
	
	private void processProperty(Element ele,BeanDefinition beanDefinition) {
        NodeList propertyNode = ele.getElementsByTagName("property");
        List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
        for (int i = 0; i < propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if (node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");
                if(value!=null && value.length()>0){
                	propertyValues.add(new PropertyValue(name,value));
                }else{
                	String ref = propertyEle.getAttribute("ref");
                	BeanReference beanReference = new BeanReference(ref);
                	propertyValues.add(new PropertyValue(name,beanReference));
                }
            }
        }
        beanDefinition.setPropertyValues(propertyValues);
    }

	public Map<String, BeanDefinition> getRegistry() {
		return registry;
	}

	public void setRegistry(Map<String, BeanDefinition> registry) {
		this.registry = registry;
	}
	
	
}
