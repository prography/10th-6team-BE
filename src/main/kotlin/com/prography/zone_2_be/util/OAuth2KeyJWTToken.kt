//package com.prography.zone_2_be.util
//
//import org.springframework.security.authentication.AbstractAuthenticationToken
//import org.springframework.security.core.GrantedAuthority
//
//
//class NativeAppOAuth2AuthenticationToken(
//    authorities: Collection<GrantedAuthority>,
//    val principal : Any,
//    val credentials : Any,
//) : AbstractAuthenticationToken(authorities) {
//
//    override fun getPrincipal(): Any {
//        return principal
//    }
//
//    override fun getCredentials(): Any {
//        return credentials
//    }
//
//    @Throws(IllegalArgumentException::class)
//    override fun setAuthenticated(isAuthenticated: Boolean) {
//        require(!isAuthenticated) {
//            "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead."
//        }
//        super.setAuthenticated(false)
//    }
//
//}