/**
 * Autogenerated by Avro
 * <p>
 * DO NOT EDIT DIRECTLY
 */
package com.rinkul.avro.schema;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class EmployeeRecord extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"EmployeeRecord\",\"namespace\":\"com.rinkul.avro.schema\",\"fields\":[{\"name\":\"empId\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"firstName\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"lastName\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"email\",\"type\":[\"null\",\"string\"],\"default\":null}]}");
    private static final long serialVersionUID = 1642131885569511245L;
    private static SpecificData MODEL$ = new SpecificData();
    private static final BinaryMessageEncoder<EmployeeRecord> ENCODER =
            new BinaryMessageEncoder<EmployeeRecord>(MODEL$, SCHEMA$);
    private static final BinaryMessageDecoder<EmployeeRecord> DECODER =
            new BinaryMessageDecoder<EmployeeRecord>(MODEL$, SCHEMA$);
    @SuppressWarnings("unchecked")
    private static final org.apache.avro.io.DatumWriter<EmployeeRecord>
            WRITER$ = (org.apache.avro.io.DatumWriter<EmployeeRecord>) MODEL$.createDatumWriter(SCHEMA$);
    @SuppressWarnings("unchecked")
    private static final org.apache.avro.io.DatumReader<EmployeeRecord>
            READER$ = (org.apache.avro.io.DatumReader<EmployeeRecord>) MODEL$.createDatumReader(SCHEMA$);
    @Deprecated
    public java.lang.Long empId;
    @Deprecated
    public java.lang.CharSequence firstName;
    @Deprecated
    public java.lang.CharSequence lastName;
    @Deprecated
    public java.lang.CharSequence email;

    /**
     * Default constructor.  Note that this does not initialize fields
     * to their default values from the schema.  If that is desired then
     * one should use <code>newBuilder()</code>.
     */
    public EmployeeRecord() {
    }

    /**
     * All-args constructor.
     *
     * @param empId     The new value for empId
     * @param firstName The new value for firstName
     * @param lastName  The new value for lastName
     * @param email     The new value for email
     */
    public EmployeeRecord(java.lang.Long empId, java.lang.CharSequence firstName, java.lang.CharSequence lastName, java.lang.CharSequence email) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static org.apache.avro.Schema getClassSchema() {
        return SCHEMA$;
    }

    /**
     * Return the BinaryMessageDecoder instance used by this class.
     */
    public static BinaryMessageDecoder<EmployeeRecord> getDecoder() {
        return DECODER;
    }

    /**
     * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
     *
     * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
     */
    public static BinaryMessageDecoder<EmployeeRecord> createDecoder(SchemaStore resolver) {
        return new BinaryMessageDecoder<EmployeeRecord>(MODEL$, SCHEMA$, resolver);
    }

    /**
     * Deserializes a EmployeeRecord from a ByteBuffer.
     */
    public static EmployeeRecord fromByteBuffer(
            java.nio.ByteBuffer b) throws java.io.IOException {
        return DECODER.decode(b);
    }

    /**
     * Creates a new EmployeeRecord RecordBuilder.
     *
     * @return A new EmployeeRecord RecordBuilder
     */
    public static com.rinkul.avro.schema.EmployeeRecord.Builder newBuilder() {
        return new com.rinkul.avro.schema.EmployeeRecord.Builder();
    }

    /**
     * Creates a new EmployeeRecord RecordBuilder by copying an existing Builder.
     *
     * @param other The existing builder to copy.
     * @return A new EmployeeRecord RecordBuilder
     */
    public static com.rinkul.avro.schema.EmployeeRecord.Builder newBuilder(com.rinkul.avro.schema.EmployeeRecord.Builder other) {
        return new com.rinkul.avro.schema.EmployeeRecord.Builder(other);
    }

    /**
     * Creates a new EmployeeRecord RecordBuilder by copying an existing EmployeeRecord instance.
     *
     * @param other The existing instance to copy.
     * @return A new EmployeeRecord RecordBuilder
     */
    public static com.rinkul.avro.schema.EmployeeRecord.Builder newBuilder(com.rinkul.avro.schema.EmployeeRecord other) {
        return new com.rinkul.avro.schema.EmployeeRecord.Builder(other);
    }

    /**
     * Serializes this EmployeeRecord to a ByteBuffer.
     */
    public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
        return ENCODER.encode(this);
    }

    public org.apache.avro.Schema getSchema() {
        return SCHEMA$;
    }

    // Used by DatumWriter.  Applications should not call.
    public java.lang.Object get(int field$) {
        switch (field$) {
            case 0:
                return empId;
            case 1:
                return firstName;
            case 2:
                return lastName;
            case 3:
                return email;
            default:
                throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    // Used by DatumReader.  Applications should not call.
    @SuppressWarnings(value = "unchecked")
    public void put(int field$, java.lang.Object value$) {
        switch (field$) {
            case 0:
                empId = (java.lang.Long) value$;
                break;
            case 1:
                firstName = (java.lang.CharSequence) value$;
                break;
            case 2:
                lastName = (java.lang.CharSequence) value$;
                break;
            case 3:
                email = (java.lang.CharSequence) value$;
                break;
            default:
                throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    /**
     * Gets the value of the 'empId' field.
     *
     * @return The value of the 'empId' field.
     */
    public java.lang.Long getEmpId() {
        return empId;
    }

    /**
     * Sets the value of the 'empId' field.
     *
     * @param value the value to set.
     */
    public void setEmpId(java.lang.Long value) {
        this.empId = value;
    }

    /**
     * Gets the value of the 'firstName' field.
     *
     * @return The value of the 'firstName' field.
     */
    public java.lang.CharSequence getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the 'firstName' field.
     *
     * @param value the value to set.
     */
    public void setFirstName(java.lang.CharSequence value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the 'lastName' field.
     *
     * @return The value of the 'lastName' field.
     */
    public java.lang.CharSequence getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the 'lastName' field.
     *
     * @param value the value to set.
     */
    public void setLastName(java.lang.CharSequence value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the 'email' field.
     *
     * @return The value of the 'email' field.
     */
    public java.lang.CharSequence getEmail() {
        return email;
    }

    /**
     * Sets the value of the 'email' field.
     *
     * @param value the value to set.
     */
    public void setEmail(java.lang.CharSequence value) {
        this.email = value;
    }

    @Override
    public void writeExternal(java.io.ObjectOutput out)
            throws java.io.IOException {
        WRITER$.write(this, SpecificData.getEncoder(out));
    }

    @Override
    public void readExternal(java.io.ObjectInput in)
            throws java.io.IOException {
        READER$.read(this, SpecificData.getDecoder(in));
    }

    /**
     * RecordBuilder for EmployeeRecord instances.
     */
    public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<EmployeeRecord>
            implements org.apache.avro.data.RecordBuilder<EmployeeRecord> {

        private java.lang.Long empId;
        private java.lang.CharSequence firstName;
        private java.lang.CharSequence lastName;
        private java.lang.CharSequence email;

        /**
         * Creates a new Builder
         */
        private Builder() {
            super(SCHEMA$);
        }

        /**
         * Creates a Builder by copying an existing Builder.
         *
         * @param other The existing Builder to copy.
         */
        private Builder(com.rinkul.avro.schema.EmployeeRecord.Builder other) {
            super(other);
            if (isValidValue(fields()[0], other.empId)) {
                this.empId = data().deepCopy(fields()[0].schema(), other.empId);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.firstName)) {
                this.firstName = data().deepCopy(fields()[1].schema(), other.firstName);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.lastName)) {
                this.lastName = data().deepCopy(fields()[2].schema(), other.lastName);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.email)) {
                this.email = data().deepCopy(fields()[3].schema(), other.email);
                fieldSetFlags()[3] = true;
            }
        }

        /**
         * Creates a Builder by copying an existing EmployeeRecord instance
         *
         * @param other The existing instance to copy.
         */
        private Builder(com.rinkul.avro.schema.EmployeeRecord other) {
            super(SCHEMA$);
            if (isValidValue(fields()[0], other.empId)) {
                this.empId = data().deepCopy(fields()[0].schema(), other.empId);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.firstName)) {
                this.firstName = data().deepCopy(fields()[1].schema(), other.firstName);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.lastName)) {
                this.lastName = data().deepCopy(fields()[2].schema(), other.lastName);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.email)) {
                this.email = data().deepCopy(fields()[3].schema(), other.email);
                fieldSetFlags()[3] = true;
            }
        }

        /**
         * Gets the value of the 'empId' field.
         *
         * @return The value.
         */
        public java.lang.Long getEmpId() {
            return empId;
        }

        /**
         * Sets the value of the 'empId' field.
         *
         * @param value The value of 'empId'.
         * @return This builder.
         */
        public com.rinkul.avro.schema.EmployeeRecord.Builder setEmpId(java.lang.Long value) {
            validate(fields()[0], value);
            this.empId = value;
            fieldSetFlags()[0] = true;
            return this;
        }

        /**
         * Checks whether the 'empId' field has been set.
         *
         * @return True if the 'empId' field has been set, false otherwise.
         */
        public boolean hasEmpId() {
            return fieldSetFlags()[0];
        }


        /**
         * Clears the value of the 'empId' field.
         *
         * @return This builder.
         */
        public com.rinkul.avro.schema.EmployeeRecord.Builder clearEmpId() {
            empId = null;
            fieldSetFlags()[0] = false;
            return this;
        }

        /**
         * Gets the value of the 'firstName' field.
         *
         * @return The value.
         */
        public java.lang.CharSequence getFirstName() {
            return firstName;
        }

        /**
         * Sets the value of the 'firstName' field.
         *
         * @param value The value of 'firstName'.
         * @return This builder.
         */
        public com.rinkul.avro.schema.EmployeeRecord.Builder setFirstName(java.lang.CharSequence value) {
            validate(fields()[1], value);
            this.firstName = value;
            fieldSetFlags()[1] = true;
            return this;
        }

        /**
         * Checks whether the 'firstName' field has been set.
         *
         * @return True if the 'firstName' field has been set, false otherwise.
         */
        public boolean hasFirstName() {
            return fieldSetFlags()[1];
        }


        /**
         * Clears the value of the 'firstName' field.
         *
         * @return This builder.
         */
        public com.rinkul.avro.schema.EmployeeRecord.Builder clearFirstName() {
            firstName = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        /**
         * Gets the value of the 'lastName' field.
         *
         * @return The value.
         */
        public java.lang.CharSequence getLastName() {
            return lastName;
        }

        /**
         * Sets the value of the 'lastName' field.
         *
         * @param value The value of 'lastName'.
         * @return This builder.
         */
        public com.rinkul.avro.schema.EmployeeRecord.Builder setLastName(java.lang.CharSequence value) {
            validate(fields()[2], value);
            this.lastName = value;
            fieldSetFlags()[2] = true;
            return this;
        }

        /**
         * Checks whether the 'lastName' field has been set.
         *
         * @return True if the 'lastName' field has been set, false otherwise.
         */
        public boolean hasLastName() {
            return fieldSetFlags()[2];
        }


        /**
         * Clears the value of the 'lastName' field.
         *
         * @return This builder.
         */
        public com.rinkul.avro.schema.EmployeeRecord.Builder clearLastName() {
            lastName = null;
            fieldSetFlags()[2] = false;
            return this;
        }

        /**
         * Gets the value of the 'email' field.
         *
         * @return The value.
         */
        public java.lang.CharSequence getEmail() {
            return email;
        }

        /**
         * Sets the value of the 'email' field.
         *
         * @param value The value of 'email'.
         * @return This builder.
         */
        public com.rinkul.avro.schema.EmployeeRecord.Builder setEmail(java.lang.CharSequence value) {
            validate(fields()[3], value);
            this.email = value;
            fieldSetFlags()[3] = true;
            return this;
        }

        /**
         * Checks whether the 'email' field has been set.
         *
         * @return True if the 'email' field has been set, false otherwise.
         */
        public boolean hasEmail() {
            return fieldSetFlags()[3];
        }


        /**
         * Clears the value of the 'email' field.
         *
         * @return This builder.
         */
        public com.rinkul.avro.schema.EmployeeRecord.Builder clearEmail() {
            email = null;
            fieldSetFlags()[3] = false;
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public EmployeeRecord build() {
            try {
                EmployeeRecord record = new EmployeeRecord();
                record.empId = fieldSetFlags()[0] ? this.empId : (java.lang.Long) defaultValue(fields()[0]);
                record.firstName = fieldSetFlags()[1] ? this.firstName : (java.lang.CharSequence) defaultValue(fields()[1]);
                record.lastName = fieldSetFlags()[2] ? this.lastName : (java.lang.CharSequence) defaultValue(fields()[2]);
                record.email = fieldSetFlags()[3] ? this.email : (java.lang.CharSequence) defaultValue(fields()[3]);
                return record;
            } catch (java.lang.Exception e) {
                throw new org.apache.avro.AvroRuntimeException(e);
            }
        }
    }

}
