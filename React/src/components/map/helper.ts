export const getPositionError = (err: any) => {
    if (
        err.code === 1 || //if user denied accessing the location
        err.code === 2 || //for any internal errors
        err.code === 3 //error due to timeout
    ) {
        alert(err.message)
    } else {
        alert(err)
    }
}

