package core.workers;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import core.workers.PdfWorker.PdfWorkerLocal;
import core.workers.PdfWorker.PdfWorkerRemote;
import core.workers.TransformWorker.TransformWorkerLocal;

/**
 * Worker implementation.
 * @author  malczyk.pawel@gmail.com
 */
@Stateless
@Local(PdfWorkerLocal.class)
@Remote(PdfWorkerRemote.class)
public class PdfWorkerBean extends AbstractWorkerBean implements PdfWorker{

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PdfWorkerBean.class);

	/**
	 * Transform transformId.
	 * @uml.property  name="transformWorkerLocal"
	 * @uml.associationEnd  
	 */
	@EJB
	private TransformWorkerLocal transformWorkerLocal;
	
	/**
	 * Constuctor.
	 */
	public PdfWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public byte[] getPdf(int transformId, byte[] xmlData) {
		byte[] result=null;
		try {
			ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream();
			BufferedOutputStream outStream = new BufferedOutputStream(byteArrOut);
			FopFactory fopFactory = FopFactory.newInstance();
			try {
				Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, outStream);
				TransformerFactory factory = TransformerFactory.newInstance();
				byte[] transformData = transformWorkerLocal.transformContent(transformId);
				Transformer transformer = factory.newTransformer(new SAXSource(new InputSource(
								new ByteArrayInputStream(transformData))));

				Result res = new SAXResult(fop.getDefaultHandler());

				Source src = new SAXSource(new InputSource(new ByteArrayInputStream(xmlData)));

				transformer.transform(src, res);
				outStream.flush();
				byteArrOut.flush();
				result = byteArrOut.toByteArray();
			} catch (FOPException e) {
				LOG.error(e.getMessage());
			} catch (TransformerConfigurationException e) {
				LOG.error(e.getMessage());
			} catch (TransformerException e) {
				LOG.error(e.getMessage());
			} finally {
				outStream.close();
			}
		} catch (FileNotFoundException e1) {
			LOG.error(e1.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}

		return result;
	}

}
