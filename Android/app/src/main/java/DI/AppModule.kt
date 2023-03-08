package DI

import Controllers.AuthController
import Controllers.Interfaces.IAuthController
import Repositories.AuthRepository
import Repositories.Interfaces.IAuthRepository
import org.koin.dsl.module

val appModule = module {
    single<IAuthController> { AuthController(get()) }
    single<IAuthRepository> { AuthRepository() }
}