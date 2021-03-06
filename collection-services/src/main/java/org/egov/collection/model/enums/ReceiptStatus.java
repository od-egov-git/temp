/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) 2016  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.collection.model.enums;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ReceiptStatus {
    APPROVED("Approved", Category.OPEN),
    APPROVALPENDING("Approval Pending",Category.OPEN),
    TOBESUBMITTED("To be Submitted", Category.OPEN),
	REMITTED("Remitted", Category.OPEN),
	REJECTED("Rejected", Category.CLOSED),
	CANCELLED("Cancelled",Category.CLOSED),
	DISHONOURED("Dishonoured", Category.CLOSED);

    
	private String value;

    private Category category;

	ReceiptStatus(String value, Category category) {
		this.value = value;
		this.category = category;
	}

    public boolean isCategory(Category category) {
        return this.category == category;
    }

    public static Set<String> statusesByCategory(Category category) {
	    Set<String> statuses = new HashSet<>();
        for (ReceiptStatus b : ReceiptStatus.values()) {
            if (b.category == category) {
                statuses.add(b.value);
            }
        }

        return statuses;
    }

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static ReceiptStatus fromValue(String text) {
		for (ReceiptStatus b : ReceiptStatus.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

	public enum Category {
		OPEN,
		CLOSED;
	}
}