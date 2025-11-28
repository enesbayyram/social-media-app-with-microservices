export interface RootEntity<T> {
    status: boolean,
    data: T,
    errorMessage: string
}