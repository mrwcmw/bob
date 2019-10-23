package gov.moonbase.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Representation of requested change to an entity
 *
 * JSON:
 * <pre>
 * {@code
 *   {
 *     "op": "replace",
 *     "path": "status",
 *     "value": "stopped"
 *   }
 * }
 * </pre>
 */

public class Patch {

    private String op;
    private String path;
    private String value;

    /**
     * Default Constructor
     */
    public Patch(){}

    /**
     * Create a Patch object from a string representing the desired operation,
     * attribute name to be changed, and the value to which that attribute should
     * be changed to
     *
     * @param op type of operation requested
     * @param path name of the attribute requested to be changed
     * @param value the value to which the above attribute should be changed to
     */
    public Patch(String op, String path, String value) {
        this.op = op;
        this.path = path;
        this.value = value;
    }

    public void setOp (String op) {
        this.op = op;
    }

    @ApiModelProperty(value="Operation (remove, replace, etc)", dataType="string", position=1, required=true, example="replace")
    public String getOp() {
        return op;
    }

    public void setPath (String path) {
        this.path = path;
    }

    @ApiModelProperty(value="Attribute to be operated on", dataType="string", position=2, required=true, example="name")
    public String getPath() {
        return path;
    }

    public void setValue (String value) {
        this.value = value;
    }

    @ApiModelProperty(value="New Attribute Value", dataType="string", position=3, required=true, example="monkey")
    public String getValue() {
        return value;
    }
}
