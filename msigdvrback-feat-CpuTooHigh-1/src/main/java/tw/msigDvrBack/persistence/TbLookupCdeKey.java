package tw.msigDvrBack.persistence;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TbLookupCdeKey implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_lookup_cde.lookup_type
     *
     * @mbg.generated Tue Sep 25 16:42:56 CST 2018
     */
    private String lookupType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_lookup_cde.lookup_cde
     *
     * @mbg.generated Tue Sep 25 16:42:56 CST 2018
     */
    private String lookupCde;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_lookup_cde.lookup_type
     *
     * @return the value of tb_lookup_cde.lookup_type
     *
     * @mbg.generated Tue Sep 25 16:42:56 CST 2018
     */
    public String getLookupType() {
        return lookupType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_lookup_cde.lookup_type
     *
     * @param lookupType the value for tb_lookup_cde.lookup_type
     *
     * @mbg.generated Tue Sep 25 16:42:56 CST 2018
     */
    public void setLookupType(String lookupType) {
        this.lookupType = lookupType == null ? null : lookupType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_lookup_cde.lookup_cde
     *
     * @return the value of tb_lookup_cde.lookup_cde
     *
     * @mbg.generated Tue Sep 25 16:42:56 CST 2018
     */
    public String getLookupCde() {
        return lookupCde;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_lookup_cde.lookup_cde
     *
     * @param lookupCde the value for tb_lookup_cde.lookup_cde
     *
     * @mbg.generated Tue Sep 25 16:42:56 CST 2018
     */
    public void setLookupCde(String lookupCde) {
        this.lookupCde = lookupCde == null ? null : lookupCde.trim();
    }
}