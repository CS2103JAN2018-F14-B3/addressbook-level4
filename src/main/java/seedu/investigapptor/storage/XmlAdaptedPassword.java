package seedu.investigapptor.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.investigapptor.model.Password;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPassword {

    @XmlElement(required = true)
    private String currentPassword;

    /**
     * Constructs an XmlAdaptedPassword.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPassword() {}

    /**
     * Constructs an {@code XmlAdaptedPassword} with the given password.
     */
    public XmlAdaptedPassword(Password password) {
        this.currentPassword = password.getPassword();
    }

    /**
     * Converts this jaxb-friendly adapted password object into the model's Password object.
     *
     */
    public Password toModelType() {
        return new Password(currentPassword);
    }

    /**
     * Updates the password given a new password
     * @param password is the password to be changed to
     */
    public void updatePassword(Password password) {
        this.currentPassword = password.getPassword();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPassword)) {
            return false;
        }

        XmlAdaptedPassword otherPassword = (XmlAdaptedPassword) other;
        return Objects.equals(currentPassword, otherPassword.currentPassword);
    }
}