package com.entity5.phonex.api

import com.entity5.phonex.{OAuth2AuthenticatedResource, VliyaStack}

/**
 * Created by mabdullah on 9/9/14.
 */
class UsersServlet extends VliyaStack with OAuth2AuthenticatedResource {

  get("/"){
    "You want the list of users?"
  }
}
