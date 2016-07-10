/*
 * Name: John Starich
 * EID: js68634
 */

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
	/**
	 * Determines whether a candidate Matching represents a solution to the
	 * Stable Matching problem. Study the description of a Matching in the
	 * project documentation to help you with this.
	 */
	public boolean isStableMatching(Matching match) {
		// stable matching = both tenant and landlord don't prefer each other from different matchings

		// for every tenant, make sure there isn't an unstable match
		for(int tenant = 0; tenant < match.getTenantCount(); tenant++) {
			int apartment = match.getTenantMatching().get(tenant);                  // current matched apartment
			int apartmentPref = match.getTenantPref().get(tenant).get(apartment);   // current tenant's thoughts on this apartment

			// build a collection of apartments that the tenant prefers to the current apartment
			Vector<Integer> apartmentsTenantPrefersToCurrent = new Vector<>();
			Vector<Integer> tenantPreferences = match.getTenantPref().get(tenant);
			for(int apartmentIndex = 0; apartmentIndex < tenantPreferences.size(); apartmentIndex++) {
				if(apartmentIndex != apartment && tenantPreferences.get(apartmentIndex) < apartmentPref) {         // higher preference (lower #)
					apartmentsTenantPrefersToCurrent.add(apartmentIndex);
				}
			}
			//System.out.println("Tenant "+tenant+": current="+apartment+", preferred="+apartmentsTenantPrefersToCurrent);

			// search through these preferred apartments for a landlord that also prefers the current tenant
			for(int preferredApartment : apartmentsTenantPrefersToCurrent) {
				int landlord;
				// find the landlord for this preferred apartment
				for(landlord = 0; landlord < match.getLandlordCount(); landlord++) {
					if(match.getLandlordOwners().get(landlord).contains(preferredApartment)) {
						break;
					}
				}
				//System.out.println("Tenant "+tenant+": preferredApartment="+preferredApartment+" -> landlord="+landlord);

				// find out if this tenant would prefer this tenant over the currently matched tenant
				Vector<Integer> landlordPrefs = match.getLandlordPref().get(landlord);
				int landlordPreferenceForTenant = landlordPrefs.get(tenant);
				int currentTenant;
				for(currentTenant = 0; currentTenant < match.getTenantMatching().size(); currentTenant++) {
					if(match.getTenantMatching().get(currentTenant) == preferredApartment) break;
				}
				int landlordPreference = landlordPrefs.get(currentTenant);
				if (landlordPreferenceForTenant < landlordPreference) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Determines a solution to the Stable Matching problem from the given input
	 * set. Study the project description to understand the variables which
	 * represent the input to your solution.
	 *
	 * @return A stable Matching.
	 */
	public Matching stableMatchingGaleShapley(Matching match) {
		/*
		while(a tenant is free and that tenant has not proposed to all apartment options) { // "apartment options" or "landlords"?
			for(preferredApartment : tenantPrefs) {
				if(preferredApartment is available) {
					matches[tenant] = preferredApartment;
					break;
				}
				if(currentLandownerOfPreferredApartment prefers new tenant) {
					matches[tenant] = preferredTenant;
					matches[otherTenant] = null;
					break;
				}
			}
		}
		return new Matching(match, matches);
		*/
		final int t = match.getTenantCount();

		Vector<Integer> matches = new Vector<>();
		Vector<Set<Integer>> matchProposals = new Vector<>(t);
		for(int i = 0; i < t; i++) {
			matches.add(null);
			matchProposals.add(new HashSet<Integer>());
		}

		int freeTenants = t;
		int proposedToAll = 0;
		while(freeTenants != 0 && proposedToAll != t) {
			int freeTenant;
			for(freeTenant = 0; freeTenant < matches.size(); freeTenant++) {
				if(matches.get(freeTenant) == null) break;
			}

			Vector<Integer> freeTenantPrefs = match.getTenantPref().get(freeTenant);
//			for()
		}
		return null; /** TODO Remove this statement! */
	}
}
