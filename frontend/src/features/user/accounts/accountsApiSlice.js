import { apiSlice } from "../../../app/api/apiSlice";

export const accountsApiSlice = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    getAccounts: builder.query({
      query: () => "/api/account",
      keepUnusedDataFor: 5,
    }),
  }),
});

export const { useGetAccountsQuery } = accountsApiSlice;
