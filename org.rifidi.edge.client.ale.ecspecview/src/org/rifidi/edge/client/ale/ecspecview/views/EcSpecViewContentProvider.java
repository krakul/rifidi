/* 
 *  EcSpecViewContentProvider.java
 *  Created:	Mar 12, 2009
 *  Project:	RiFidi org.rifidi.edge.client.ale.ecspecview
 *  				http://www.rifidi.org
 *  				http://rifidi.sourceforge.net
 *  Copyright:	Pramari LLC and the Rifidi Project
 *  License:	Lesser GNU Public License (LGPL)
 *  				http://www.opensource.org/licenses/lgpl-license.html
 */
package org.rifidi.edge.client.ale.ecspecview.views;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.rifidi.edge.client.ale.api.proxy.AleProxyFactory;
import org.rifidi.edge.client.ale.api.wsdl.ale.epcglobal.EmptyParms;
import org.rifidi.edge.client.ale.api.wsdl.ale.epcglobal.GetECSpec;
import org.rifidi.edge.client.ale.api.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.rifidi.edge.client.ale.api.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
import org.rifidi.edge.client.ale.api.wsdl.ale.epcglobal.SecurityExceptionResponse;
import org.rifidi.edge.client.ale.api.xsd.ale.epcglobal.ECSpec;

/**
 * @author Tobias Hoppenthaler - tobias@pramari.com
 * 
 */
public class EcSpecViewContentProvider implements ITreeContentProvider {

	/**
	 * 
	 */
	public EcSpecViewContentProvider() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {

		if (parentElement instanceof AleProxyFactory) {
			try {
				ArrayList<String> strings = (ArrayList<String>) ((AleProxyFactory) parentElement)
						.getAleServicePortType().getECSpecNames(
								new EmptyParms()).getString();
				ECSpec[] ecSpecs = new ECSpec[strings.size()];
				int i = 0;
				for (String string : strings) {
					GetECSpec parms = new GetECSpec();
					parms.setSpecName(string);
					ecSpecs[i] = ((AleProxyFactory) parentElement)
							.getAleServicePortType().getECSpec(parms);
					i++;
				}
				return ecSpecs;

			} catch (ImplementationExceptionResponse e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityExceptionResponse e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchNameExceptionResponse e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
	 * )
	 */
	@Override
	public Object getParent(Object arg0) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof AleProxyFactory) {
			ArrayList<String> strings;
			try {
				strings = (ArrayList<String>) ((AleProxyFactory) element)
						.getAleServicePortType().getECSpecNames(
								new EmptyParms()).getString();
				if (strings.size() > 0)
					return true;
				else
					return false;
			} catch (ImplementationExceptionResponse e) {
				e.printStackTrace();
			} catch (SecurityExceptionResponse e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
	 * .lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof AleProxyFactory) {
			if (((AleProxyFactory) inputElement).getBaseUrl().isEmpty()) {
				Object[] obj = new Object[1];
				obj[0] = new AleProxyFactory(
						"http://localhost:8080/fc-server-0.4.0/services");
				return obj;
			}

			try {
				ArrayList<String> strings = (ArrayList<String>) ((AleProxyFactory) inputElement)
						.getAleServicePortType().getECSpecNames(
								new EmptyParms()).getString();
				ECSpec[] ecSpecs = new ECSpec[strings.size()];
				int i = 0;
				for (String string : strings) {
					GetECSpec parms = new GetECSpec();
					parms.setSpecName(string);
					ecSpecs[i] = ((AleProxyFactory) inputElement)
							.getAleServicePortType().getECSpec(parms);
					i++;
				}
				return ecSpecs;

			} catch (ImplementationExceptionResponse e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityExceptionResponse e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchNameExceptionResponse e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

}