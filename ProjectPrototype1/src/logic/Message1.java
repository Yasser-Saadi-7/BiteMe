package logic;

import java.io.Serializable;

public class Message1 implements Serializable {
    private static final long serialVersionUID = 1L;

    private MessageType messageType; // Type of the message
    private Object object; // The associated object with the message

    /**
     * Constructs a new Message1 instance with the specified message type and associated object.
     *
     * @param messageType the type of the message
     * @param object the associated object
     */
    public Message1(MessageType messageType, Object object) {
        super();
        this.messageType = messageType;
        this.object = object;
    }

    /**
     * Gets the message type.
     *
     * @return the messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * Sets the message type.
     *
     * @param messageType the message type to set
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * Gets the associated object.
     *
     * @return the object associated with the message
     */
    public Object getObject() {
        return object;
    }

    /**
     * Sets the associated object.
     *
     * @param object the object to set
     */
    public void setObject(Object object) {
        this.object = object;
    }
}
