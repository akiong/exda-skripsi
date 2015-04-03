dataSource {	pooled = true	}
hibernate {
    		cache.use_second_level_cache = true
			cache.use_query_cache = true
			//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
			cache.provider_class = 'org.hibernate.cache.EhCacheProvider'
			jdbc.batch_size = 100
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
//            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
			jndiName = "java:comp/env/jdbc/exdarDS"
			dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
//            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
			jndiName = "java:comp/env/jdbc/exdarDS"
			dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
        }
    }
}
