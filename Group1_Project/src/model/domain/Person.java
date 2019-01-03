package model.domain;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 3665880920647848288L;
	private String firstName;
	private String lastName;
	private String telephone;
	private String fullName;

	private Address address;
	public Person(String f, String l, String t, Address a) {
		firstName = f;
		lastName = l;
		telephone = t;
		address = a;
	}
	public String getFullName() {
		return firstName + " " + lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getTelephone() {
		return telephone;
	}
	public Address getAddress() {
		return address;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return firstName +" "+ lastName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		return true;
	}

}
