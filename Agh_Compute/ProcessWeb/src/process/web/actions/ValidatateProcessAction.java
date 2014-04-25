package process.web.actions;

import java.util.List;

import computation.model.ValidationSchema;
import computation.worker.ValidationWorker;

import core.utils.StringUtils;

/**
 * Validates package or configuration.
 * @author  malczyk.pawel@gmail.com
 */
public class ValidatateProcessAction extends AbstractProcessAction {


	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1427741893740531377L;
	
	/**
	 * Validatied configurationId.
	 * @uml.property  name="computationConfigId"
	 */
	private String computationConfigId;
	
	/**
	 * Validated package identifier.
	 * @uml.property  name="computationPackageId"
	 */
	private String computationPackageId;
	
	/**
	 * Operation.
	 * @uml.property  name="operation"
	 */
	private String operation;
	
	/**
	 * Schemas.
	 * @uml.property  name="schemas"
	 */
	private List<ValidationSchema> schemas;
	
	/**
	 * Schema id.
	 * @uml.property  name="schemaId"
	 */
	private String schemaId;
	
	
	/**
	 * Constructor.
	 */
	public ValidatateProcessAction() {
		super();
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		boolean validationResult = true;
		String viewResult = SUCCESS;
		boolean isValidating = "validate".equals(operation);
		if(isValidating) {
			if(StringUtils.isNotEmpty(computationConfigId)) {
				validationResult = locate(ValidationWorker.class).validateConfigutaion(Integer.valueOf(computationConfigId), Integer.valueOf(schemaId));
				viewResult = validationResult ? "config_validated" : "fail";
			} else if(StringUtils.isNotEmpty(computationPackageId)) {
				validationResult = locate(ValidationWorker.class).validatePackage(Integer.valueOf(computationPackageId), Integer.valueOf(schemaId));
				viewResult = validationResult ? "package_validated" : "fail";
			}
		}
		schemas = locate(ValidationWorker.class).retrieveSchemas(null);
		
		
		return viewResult;
	}


	/**
	 * Getter.
	 * @return  the computationConfigId
	 * @uml.property  name="computationConfigId"
	 */
	public String getComputationConfigId() {
		return computationConfigId;
	}


	/**
	 * Setter.
	 * @param computationConfigId  the computationConfigId to set
	 * @uml.property  name="computationConfigId"
	 */
	public void setComputationConfigId(String computationConfigId) {
		this.computationConfigId = computationConfigId;
	}


	/**
	 * Getter.
	 * @return  the computationPackageId
	 * @uml.property  name="computationPackageId"
	 */
	public String getComputationPackageId() {
		return computationPackageId;
	}


	/**
	 * Setter.
	 * @param computationPackageId  the computationPackageId to set
	 * @uml.property  name="computationPackageId"
	 */
	public void setComputationPackageId(String computationPackageId) {
		this.computationPackageId = computationPackageId;
	}


	/**
	 * Getter.
	 * @return  the operation
	 * @uml.property  name="operation"
	 */
	public String getOperation() {
		return operation;
	}


	/**
	 * Setter.
	 * @param operation  the operation to set
	 * @uml.property  name="operation"
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}


	/**
	 * Getter.
	 * @return  the schemas
	 * @uml.property  name="schemas"
	 */
	public List<ValidationSchema> getSchemas() {
		return schemas;
	}


	/**
	 * Setter.
	 * @param schemas  the schemas to set
	 * @uml.property  name="schemas"
	 */
	public void setSchemas(List<ValidationSchema> schemas) {
		this.schemas = schemas;
	}


	/**
	 * Getter.
	 * @return  the schemaId
	 * @uml.property  name="schemaId"
	 */
	public String getSchemaId() {
		return schemaId;
	}


	/**
	 * Setter.
	 * @param schemaId  the schemaId to set
	 * @uml.property  name="schemaId"
	 */
	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}

}
