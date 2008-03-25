/* **************************************************************************** 
 * CIShell: Cyberinfrastructure Shell, An Algorithm Integration Framework.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Apache License v2.0 which accompanies
 * this distribution, and is available at:
 * http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * Created on Feb 8, 2008 at Indiana University.
 * 
 * Contributors:
 *     Indiana University - Initial API
 * ***************************************************************************/
package org.cishell.framework.algorithm;

/**
 * An exception which is thrown when an error occurs in the process of executing
 * an {@link Algorithm}
 * 
 * @author Bruce Herr (bh2@bh2.net)
 */
public class AlgorithmExecutionException extends Exception {
	private static final long serialVersionUID = 9017277008277139930L;

	public AlgorithmExecutionException(Exception exception) {
		super(exception);
	}
	
	public AlgorithmExecutionException(String message) {
		super(message);
	}
}
