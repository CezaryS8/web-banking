import { apiSlice } from "../../../app/api/apiSlice";

export const cardsApiSlice = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    getCards: builder.query({
      query: (accountId) => `/api/card/account/${accountId}`,
      keepUnusedDataFor: 5,
    }),
    getCardInsensitive: builder.query({
      query: ({ accountId, cardId }) =>
        `/api/card/insensitive/account/${accountId}/card/${cardId}`,
      keepUnusedDataFor: 5,
    }),
    getCodeToCardSensitive: builder.mutation({
      query: ({ accountId, cardId }) => ({
        url: `/api/card/request-sensitive/account/${accountId}/card/${cardId}`,
        method: "POST",
      }),
      keepUnusedDataFor: 5,
    }),
    getCardSensitive: builder.mutation({
      query: ({ accountId, cardId, code }) => ({
        url: `/api/card/sensitive/account/${accountId}/card/${cardId}`,
        method: "POST",
        body: { code },
      }),
      keepUnusedDataFor: 5,
    }),
  }),
});

export const {
  useGetCardsQuery,
  useGetCardInsensitiveQuery,
  useGetCodeToCardSensitiveMutation,
  useGetCardSensitiveMutation,
} = cardsApiSlice;
