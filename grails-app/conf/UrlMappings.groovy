class UrlMappings {

	static mappings = {
		"/login/$action?"(controller: "login")
		"/logout"(controller: "logout")
		
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		
		"/api/userDetails/checkUsername"(controller: "userDetails", action: "isUsernameAvailable")
		"/api/userDetails/encryptionPassword"(controller: "userDetails", action: "encryptionPassword")
		"/api/cif/checkCorpId"(controller: "cif", action: "isCorpIdAvailable")

		"/"(view:"/index")
		
//		"/"(controller: "home", action: "index") //tampilan ke home atau root/index
		
		"500"(controller: "error", action: "serverError")
		"404"(controller: "error", action: "notFound")
		"403"(controller: "error", action: "forbidden")
		"405"(controller: "error", action: "forbidden")
	}
}
