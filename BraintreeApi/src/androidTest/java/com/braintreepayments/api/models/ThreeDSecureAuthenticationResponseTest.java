package com.braintreepayments.api.models;

import android.content.Intent;
import android.test.AndroidTestCase;

import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.threedsecure.ThreeDSecureWebViewActivity;
import com.braintreepayments.testutils.FixturesHelper;

public class ThreeDSecureAuthenticationResponseTest extends AndroidTestCase {

    public void testCanInstantiateFromJsonString() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse.fromJson(
                FixturesHelper.stringFromFixture(getContext(),
                        "three_d_secure/authentication_response.json"));

        assertEquals("11", authResponse.getCard().getLastTwo());
        assertTrue(authResponse.getCard().getThreeDSecureInfo().isLiabilityShifted());
        assertTrue(authResponse.getCard().getThreeDSecureInfo().isLiabilityShiftPossible());
        assertTrue(authResponse.isSuccess());
    }

    public void testCanInstantiateFromAnExceptionMessage() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse
                .fromException("Error!");

        assertFalse(authResponse.isSuccess());
        assertEquals("Error!", authResponse.getException());
    }

    public void testCanInstantiateFromJsonErrorString() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse.fromJson(
                FixturesHelper.stringFromFixture(getContext(),
                        "three_d_secure/authentication_response_with_error.json"));
        ErrorWithResponse errors = new ErrorWithResponse(0, authResponse.getErrors());

        assertNull(authResponse.getCard());
        assertFalse(authResponse.isSuccess());
        assertEquals("Failed to authenticate, please try a different form of payment", errors.getMessage());
    }

    public void testCanBeSerialized() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse.fromJson(
                FixturesHelper.stringFromFixture(getContext(),
                "three_d_secure/authentication_response.json"));

        Intent intent = new Intent().putExtra("auth-response", authResponse);
        ThreeDSecureAuthenticationResponse parsedAuthResponse = intent.getParcelableExtra("auth-response");

        assertEquals(authResponse.getCard().getLastTwo(), parsedAuthResponse.getCard().getLastTwo());
        assertEquals(authResponse.getCard().getThreeDSecureInfo().isLiabilityShifted(),
                parsedAuthResponse.getCard().getThreeDSecureInfo().isLiabilityShifted());
        assertEquals(authResponse.getCard().getThreeDSecureInfo().isLiabilityShiftPossible(),
                parsedAuthResponse.getCard().getThreeDSecureInfo().isLiabilityShiftPossible());
        assertEquals(authResponse.isSuccess(), parsedAuthResponse.isSuccess());
        assertEquals(authResponse.getException(), parsedAuthResponse.getException());
    }

    public void testExceptionsAreSerialized() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse
                .fromException("Error!");

        Intent intent = new Intent().putExtra("auth-response", authResponse);
        ThreeDSecureAuthenticationResponse parsedAuthResponse = intent.getParcelableExtra("auth-response");

        assertEquals(authResponse.isSuccess(), parsedAuthResponse.isSuccess());
        assertEquals(authResponse.getException(), parsedAuthResponse.getException());
    }

    public void testIsThreeDSecureAuthenticationResponseReturnsTrueForThreeDSecureAuthenticationResponses() {
        Intent intent = new Intent().putExtra(ThreeDSecureWebViewActivity.EXTRA_THREE_D_SECURE_RESULT, "");

        ThreeDSecureAuthenticationResponse.isThreeDSecureAuthenticationResponse(intent);
    }

    public void testIsThreeDSecureAuthenticationResponseReturnsFalseForNonThreeDSecureAuthenticationResponses() {
        ThreeDSecureAuthenticationResponse.isThreeDSecureAuthenticationResponse(new Intent());
    }
}
