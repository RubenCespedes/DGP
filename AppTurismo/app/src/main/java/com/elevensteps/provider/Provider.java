package com.elevensteps.provider;

/**
 * Provider interface following a CRUD (Create, Retrieve, Update, Delete) design
 * @author alejnp
 */
public interface Provider<Type, Key> {

    /**
     * Creates a new Object
     * @param obj Object to create
     * @return true if successful
     */
    boolean create(Type obj);

    /**
     * Retrieves an Object identified by an ID
     * @param id ID of the Object
     * @return java.util.Optional of the Object
     */
    java.util.Optional<Type> retrieve(Key id);

    /**
     * Retrieves all Objects
     * @return java.util.List containing all Objects
     */
    java.util.Collection<Type> retrieveAll();

    /**
     * Updates a given Object identified by an ID
     * @param id The ID of the Object to update
     * @param obj The updated Object
     * @return true if successful
     */
    boolean update(Key id, Type obj);

    /**
     *  Deletes an Object identified by an ID
     * @param id THe ID of the Object to delete
     * @return true if successful
     */
    boolean delete(Key id);
}
