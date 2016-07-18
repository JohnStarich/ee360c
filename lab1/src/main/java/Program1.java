/*
 * Name: John Starich
 * EID: js68634
 */

import java.util.*;

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

			// search through these preferred apartments for a landlord that also prefers the current tenant
			for(int preferredApartment : apartmentsTenantPrefersToCurrent) {
				int landlord;
				// find the landlord for this preferred apartment
				for(landlord = 0; landlord < match.getLandlordCount(); landlord++) {
					if(match.getLandlordOwners().get(landlord).contains(preferredApartment)) {
						break;
					}
				}

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

	/*
	initially all men and women are free
	while there is some man who is free and hasn't proposed to all women:
		m = choose that free man
		w = choose first woman in man's pref list that has not been proposed to yet
		if w is free:
			m and w become matched
		else:
			m2 = man w is matched with already
			if w prefers m2 to m:
				m remains free
			else:
				m and w become matched
				m2 becomes free
	return matches
	 */

	/**
	 * Determines a solution to the Stable Matching problem from the given input
	 * set. Study the project description to understand the variables which
	 * represent the input to your solution.
	 *
	 * @return A stable Matching.
	 */
	public Matching stableMatchingGaleShapley(Matching match) {
		final int tenantCount = match.getTenantCount();
		final int landlordCount = match.getLandlordCount();
		int apartmentCount = 0;
		for(int landlord = 0; landlord < landlordCount; landlord++) {
			apartmentCount += match.getLandlordOwners().get(landlord).size();
		}
		Map<Integer, Integer> matchings = new HashMap<>(tenantCount);
		Map<Integer, Set<Integer>> proposalsLeft = new HashMap<>(tenantCount);
		for(int tenant = 0; tenant < tenantCount; tenant++) {
			Set<Integer> unproposedApartments = new HashSet<>(apartmentCount);
			for(int apartment = 0; apartment < apartmentCount; apartment++) unproposedApartments.add(apartment);
			proposalsLeft.put(tenant, unproposedApartments);
		}

		Integer tenant;
		while ((tenant = freeTenant(matchings, tenantCount)) != null && ! proposalsLeft.get(tenant).isEmpty()) {
			Integer unproposedApartment = firstUnproposedApartment(match.getTenantPref().get(tenant), proposalsLeft.get(tenant));
			if (! matchings.values().contains(unproposedApartment)) {
				proposalsLeft.get(tenant).remove(unproposedApartment);
				matchings.put(tenant, unproposedApartment);
			}
			else {
				Integer currentTenantOfApartment = residentOfApartment(unproposedApartment, matchings);
				Integer landlord = landlordForApartment(unproposedApartment, match.getLandlordOwners());
				Vector<Integer> landlordPrefsOfTenants = match.getLandlordPref().get(landlord);
				Integer landlordPrefOfCurrentTenant = landlordPrefsOfTenants.get(currentTenantOfApartment);
				Integer landlordPrefOfNewTenant = landlordPrefsOfTenants.get(tenant);
				if(landlordPrefOfCurrentTenant < landlordPrefOfNewTenant) {
					proposalsLeft.get(tenant).remove(unproposedApartment);
				}
				else {
					matchings.remove(currentTenantOfApartment);
					proposalsLeft.get(tenant).remove(unproposedApartment);
					matchings.put(tenant, unproposedApartment);
				}
			}
		}
		match.setTenantMatching(convertMatchings(matchings));
		return match;
	}

	private Integer residentOfApartment(Integer apartment, Map<Integer, Integer> matchings) {
		for(Map.Entry<Integer, Integer> matching : matchings.entrySet()) {
			if(apartment.equals(matching.getValue())) return matching.getKey();
		}
		return null;
	}

	private Integer landlordForApartment(Integer apartment, Vector<Vector<Integer>> landlordOwnership) {
		for(int landlord = 0; landlord < landlordOwnership.size(); landlord++) {
			if(landlordOwnership.get(landlord).contains(apartment)) return landlord;
		}
		return null;
	}

	private Integer firstUnproposedApartment(Vector<Integer> preferences, Set<Integer> proposalsLeft) {
		Integer lowestPref = Integer.MAX_VALUE;
		Integer lowestPrefApartment = -1; // = highest priority apartment
		for(Integer apartment : proposalsLeft) {
			if (preferences.get(apartment) < lowestPref) {
				lowestPref = preferences.get(apartment);
				lowestPrefApartment = apartment;
			}
		}

		return lowestPrefApartment;
	}

	public Vector<Integer> convertMatchings(Map<Integer, Integer> matchings) {
		Vector<Integer> newMatchings = new Vector<>(matchings.size());
		newMatchings.addAll(matchings.values());
		for(Map.Entry<Integer, Integer> entry : matchings.entrySet()) {
			newMatchings.set(entry.getKey(), entry.getValue());
		}
		return newMatchings;
	}

	public Integer freeTenant(Map<Integer, Integer> matchings, int totalTenants) {
		for(int i = 0; i < totalTenants; i++) {
			if(! matchings.containsKey(i)) return i;
		}
		return null;
	}
}
