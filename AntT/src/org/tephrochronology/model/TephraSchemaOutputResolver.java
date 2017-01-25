/**
 * 
 */
package org.tephrochronology.model;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

/**
 * @author Mark Royer
 *
 */
public class TephraSchemaOutputResolver extends SchemaOutputResolver {

	private File schemaFile;
	
	/**
	 * 
	 */
	public TephraSchemaOutputResolver(File schemaFile) {
		this.schemaFile = schemaFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.bind.SchemaOutputResolver#createOutput(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Result createOutput(String namespaceUri, String suggestedFileName)
			throws IOException {
		StreamResult result = new StreamResult(schemaFile);
		result.setSystemId(schemaFile.toURI().toURL().toString());
		return result;
	}

}
