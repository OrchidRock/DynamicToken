package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import shibboleth.ByteConvert;


class LocalStore {
	private static String LOCAL_STORE_FILE_NAME="UserList.xml";
	
	Document document;
	
	DocumentBuilder builder;
	public LocalStore(){
		document=null;
		DocumentBuilderFactory factory;
		factory=DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setIgnoringElementContentWhitespace(true);
		try {
			builder=factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean storeNewUser(User userU){
		
		Element userE=getUserElementSpec(userU);
		if(userE!=null)
			return false;
		Element rootE=document.getDocumentElement();//get root Element
		Element newUserE=document.createElement("user");
		rootE.appendChild(newUserE);
		//name
		Element newUserNameE=document.createElement("username");
		Text textNameNode=document.createTextNode(userU.getUserName());
		newUserNameE.appendChild(textNameNode);
		newUserE.appendChild(newUserNameE);
		
		//password
		Element newUserPasswordE=document.createElement("password");
		Text textPasswordNode=document.createTextNode(userU.getUserPassword());
		newUserPasswordE.appendChild(textPasswordNode);
		newUserE.appendChild(newUserPasswordE);
		
		//sed
		Element newUserSedE=document.createElement("sed");
		Text textSedNode=document.createTextNode(ByteConvert.hexString(userU.getMysed()));
		newUserSedE.appendChild(textSedNode);
		newUserE.appendChild(newUserSedE);
		
		/*
		int count=rootE.getChildNodes().getLength();
		//id
		Element newUserIdE=document.createElement("id");
		Text textIdNode=document.createTextNode(Integer.toString(count));
		newUserIdE.appendChild(textIdNode);
		newUserE.appendChild(newUserIdE);
		userU.setId(count);
		*/
		writeBack();
		return true;
	}
	public void writeBack(){
		if(document==null){
			System.out.println("document is null");
			return ;
		}
		try {
			File file=new File(LOCAL_STORE_FILE_NAME);
			Transformer transformer= TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "users.dtd");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.transform(new DOMSource(document), new StreamResult(Files.newOutputStream(file.toPath())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getUserListCount(){
		
		if(document==null){
			try {
				File file=new File(LOCAL_STORE_FILE_NAME);
				document=builder.parse(file);
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Element rootE=document.getDocumentElement();//get UserList Element
		return rootE.getChildNodes().getLength();
		
	}
	public boolean resetUserSed(User goalUser){
		Element userE=getUserElementSpec(goalUser);
		if(userE==null)
			return false;
		Element sedE=(Element) userE.getChildNodes().item(2);
		Text pText=(Text) sedE.getFirstChild();
		pText.setData(ByteConvert.hexString(goalUser.getMysed()));
		writeBack();
		return true;
	}
	public User findAndGetOneUser(String name,String password){
		
		User goaluser=new User(name,password);
		System.err.println("find:goaluser name:"+goaluser.getUserName());
		System.err.println("find:goal password:"+goaluser.getUserPassword());
		Element element=getUserElementSpec(goaluser);
		if(element!=null){
			Element sedE=(Element)element.getChildNodes().item(2);
			byte[] sed=ByteConvert.getByteArrayByHexString(
					((Text)sedE.getFirstChild()).getData());
			goaluser.setMySed(sed);
			return goaluser;
		}
		return null;
	} 
	public void clearDom(){
		document=null;
	}
	private Element getUserElementSpec(User goaluser){
		if(goaluser==null){
			System.out.println("goaluser is null");
			return null;
		}
		try {
			File file=new File(LOCAL_STORE_FILE_NAME);
			Element rootE;
			if(!file.exists()){
				document=builder.newDocument();
				rootE=document.createElement("UserList");
				document.appendChild(rootE);//add root element
			}
			if(document==null)
				document=builder.parse(file);
		} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element rootE=document.getDocumentElement();
		User tempuserU=new User();
		NodeList users=rootE.getChildNodes();
		for(int i=0;i<users.getLength();i++){
			Element userE=(Element) users.item(i);
			Element usernameE=(Element)userE.getChildNodes().item(0);
			Element passwordE=(Element)userE.getChildNodes().item(1);
			
			tempuserU.setMyName(((Text)usernameE.getFirstChild()).getData());
			tempuserU.setMyPasswordByLocal(((Text)passwordE.getFirstChild()).getData());
			System.err.println("get:user:"+tempuserU.getUserName());
			System.err.println("get:password:"+tempuserU.getUserPassword());
			System.err.println("get:goaluser name:"+goaluser.getUserName());
			System.err.println("get:goal password:"+goaluser.getUserPassword());
			if(tempuserU.equals(goaluser)){
				return userE;
			}
		}
		return null;
	}
	/*public boolean findOneUserByName(String name){
		return false;
	}*/
}
