import { apiSlice } from "../../../app/api/apiSlice";

export const transfersApiSlice = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    createTransfer: builder.mutation({
      query: ({ fromAccountNumber, toAccountNumber, amount, title }) => ({
        url: `/api/transfer`,
        method: "POST",
        body: { fromAccountNumber, toAccountNumber, amount, title },
      }),
      keepUnusedDataFor: 5,
    }),
  }),
});

export const { useCreateTransferMutation } = transfersApiSlice;
